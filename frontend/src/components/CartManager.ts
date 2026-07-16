import { CartItem } from '../types/api';
import apiClient from '../services/apiClient';

export class CartManager {
  private items: CartItem[] = [];
  private container: HTMLElement;

  constructor(container: HTMLElement) {
    this.container = container;
  }

  async loadCart(): Promise<void> {
    try {
      const response = await apiClient.getCart();
      if (response.success && response.data) {
        this.items = response.data;
        this.render();
      }
    } catch (error) {
      console.error('Error loading cart:', error);
    }
  }

  private render(): void {
    this.container.innerHTML = '';

    if (this.items.length === 0) {
      this.container.innerHTML = '<p class="empty-cart-message">Your cart is empty</p>';
      return;
    }

    const cartTable = document.createElement('table');
    cartTable.className = 'cart-table';
    cartTable.innerHTML = `
      <thead>
        <tr>
          <th>Product</th>
          <th>Price</th>
          <th>Quantity</th>
          <th>Total</th>
          <th>Action</th>
        </tr>
      </thead>
      <tbody>
        ${this.items.map(item => `
          <tr class="cart-item">
            <td class="product-info">
              <img src="${item.image_url}" alt="${item.name}" />
              <span>${item.name}</span>
            </td>
            <td>$${item.price.toFixed(2)}</td>
            <td>
              <input type="number" class="quantity-input" value="${item.quantity}" min="1" data-cart-id="${item.id}" />
            </td>
            <td>$${(item.price * item.quantity).toFixed(2)}</td>
            <td>
              <button class="btn btn-danger remove-btn" data-cart-id="${item.id}">Remove</button>
            </td>
          </tr>
        `).join('')}
      </tbody>
    `;

    this.container.appendChild(cartTable);

    // Add event listeners
    cartTable.querySelectorAll('.quantity-input').forEach(input => {
      input.addEventListener('change', (e) => this.updateQuantity(e));
    });

    cartTable.querySelectorAll('.remove-btn').forEach(btn => {
      btn.addEventListener('click', (e) => this.removeItem(e));
    });

    // Add cart summary
    const summary = this.renderSummary();
    this.container.appendChild(summary);
  }

  private renderSummary(): HTMLElement {
    const total = this.items.reduce((sum, item) => sum + (item.price * item.quantity), 0);
    const summary = document.createElement('div');
    summary.className = 'cart-summary';
    summary.innerHTML = `
      <div class="summary-item">
        <span>Subtotal:</span>
        <span>$${total.toFixed(2)}</span>
      </div>
      <div class="summary-item">
        <span>Shipping:</span>
        <span>$10.00</span>
      </div>
      <div class="summary-total">
        <span>Total:</span>
        <span>$${(total + 10).toFixed(2)}</span>
      </div>
      <button class="btn btn-primary checkout-btn" style="width: 100%; margin-top: 15px;">Proceed to Checkout</button>
    `;

    summary.querySelector('.checkout-btn')?.addEventListener('click', () => {
      window.location.href = '/swiftcart-html/checkout.html';
    });

    return summary;
  }

  private async updateQuantity(event: Event): Promise<void> {
    const input = event.target as HTMLInputElement;
    const cartId = parseInt(input.dataset.cartId || '0');
    const quantity = parseInt(input.value);

    if (quantity <= 0) {
      await this.removeItem({ target: { dataset: { cartId: input.dataset.cartId } } } as any);
      return;
    }

    try {
      await apiClient.updateCartItem(cartId, quantity);
      await this.loadCart();
    } catch (error) {
      console.error('Error updating quantity:', error);
    }
  }

  private async removeItem(event: Event): Promise<void> {
    const btn = event.target as HTMLElement;
    const cartId = parseInt(btn.dataset.cartId || '0');

    if (confirm('Are you sure you want to remove this item?')) {
      try {
        await apiClient.removeFromCart(cartId);
        await this.loadCart();
      } catch (error) {
        console.error('Error removing item:', error);
      }
    }
  }

  getCartTotal(): number {
    return this.items.reduce((sum, item) => sum + (item.price * item.quantity), 0);
  }
}
