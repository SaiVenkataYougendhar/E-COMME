import { Product } from '../types/api';
import apiClient from '../services/apiClient';

export class ProductCard {
  private product: Product;
  private container: HTMLElement;

  constructor(product: Product, container: HTMLElement) {
    this.product = product;
    this.container = container;
  }

  render(): HTMLElement {
    const card = document.createElement('div');
    card.className = 'product-card';
    card.innerHTML = `
      <div class="product-card__image">
        <img src="${this.product.image_url}" alt="${this.product.name}" />
        ${this.product.discount_price ? `<span class="product-card__discount">${Math.round(((this.product.price - this.product.discount_price) / this.product.price) * 100)}%</span>` : ''}
      </div>
      <div class="product-card__content">
        <h3 class="product-card__title">${this.product.name}</h3>
        <div class="product-card__rating">
          ${'★'.repeat(Math.floor(this.product.rating))}${'☆'.repeat(5 - Math.floor(this.product.rating))} (${this.product.rating})
        </div>
        <div class="product-card__price">
          ${this.product.discount_price ? `<span class="original">$${this.product.price.toFixed(2)}</span>` : ''}
          <span class="current">$${(this.product.discount_price || this.product.price).toFixed(2)}</span>
        </div>
        <div class="product-card__stock">
          ${this.product.stock > 0 ? `<span class="in-stock">In Stock (${this.product.stock})</span>` : '<span class="out-of-stock">Out of Stock</span>'}
        </div>
        <div class="product-card__actions">
          <button class="btn btn-primary add-to-cart-btn" data-product-id="${this.product.id}">Add to Cart</button>
          <button class="btn btn-secondary wishlist-btn" data-product-id="${this.product.id}">♡</button>
        </div>
      </div>
    `;

    // Add event listeners
    const addToCartBtn = card.querySelector('.add-to-cart-btn') as HTMLButtonElement;
    addToCartBtn?.addEventListener('click', () => this.handleAddToCart());

    return card;
  }

  private async handleAddToCart(): Promise<void> {
    try {
      const token = localStorage.getItem('auth_token');
      if (!token) {
        alert('Please login first');
        window.location.href = '/swiftcart-html/';
        return;
      }

      const response = await apiClient.addToCart(this.product.id, 1);
      if (response.success) {
        alert('Product added to cart');
      }
    } catch (error) {
      console.error('Error adding to cart:', error);
      alert('Error adding to cart');
    }
  }
}
