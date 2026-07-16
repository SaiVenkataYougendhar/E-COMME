import apiClient from '../services/apiClient';
import { loadStripe, Stripe, StripeElements, StripeCardElement } from '@stripe/stripe-js';

export class CheckoutManager {
  private container: HTMLElement;
  private placeOrderBtn: HTMLElement | null;
  private stripe: Stripe | null = null;
  private elements: StripeElements | null = null;
  private card: StripeCardElement | null = null;
  private lat: number | null = null;
  private lng: number | null = null;

  constructor(container: HTMLElement) {
    this.container = container;
    this.placeOrderBtn = document.querySelector('.cart-checkout-btn');

    this.init();
  }

  private async init() {
    this.initStripe();
    this.initGeolocation();

    if (this.placeOrderBtn) {
      this.placeOrderBtn.addEventListener('click', async (e) => {
        e.preventDefault();
        await this.handleCheckout();
      });
    }
  }

  private async initStripe() {
    this.stripe = await loadStripe(process.env.STRIPE_PUBLIC_KEY || '');
    if (this.stripe) {
      this.elements = this.stripe.elements();
      this.card = this.elements.create('card');
      
      const paymentSection = document.querySelector('.cart-checkout:last-of-type');
      if (paymentSection) {
        const cardContainer = document.createElement('div');
        cardContainer.id = 'card-element';
        cardContainer.style.padding = '10px';
        cardContainer.style.border = '1px solid #ccc';
        cardContainer.style.marginTop = '10px';
        paymentSection.appendChild(cardContainer);
        this.card.mount('#card-element');
      }
    }
  }

  private initGeolocation() {
    if ('geolocation' in navigator) {
      navigator.geolocation.getCurrentPosition(async (position) => {
        this.lat = position.coords.latitude;
        this.lng = position.coords.longitude;
        
        try {
          const response = await fetch(`https://maps.googleapis.com/maps/api/geocode/json?latlng=${this.lat},${this.lng}&key=${process.env.GOOGLE_MAPS_API_KEY}`);
          const data = await response.json();
          if (data.results && data.results.length > 0) {
            const address = data.results[0].formatted_address;
            const addressInput = this.container.querySelector('input[placeholder="Street address"]') as HTMLInputElement;
            if (addressInput) addressInput.value = address;
          }
        } catch (error) {
          console.error("Geocoding error", error);
        }
      }, (err) => console.warn(err));
    }
  }

  private async handleCheckout() {
    try {
      const auth = localStorage.getItem('auth_token');
      if (!auth) {
        alert('Please login to place an order.');
        window.location.href = '/swiftcart-html/login.html';
        return;
      }

      if (!this.stripe || !this.card) {
        alert("Stripe is not loaded");
        return;
      }

      // Collect form data
      const inputs = this.container.querySelectorAll('.checkout-form-list input');
      let addressParts: string[] = [];
      inputs.forEach(input => {
        const val = (input as HTMLInputElement).value.trim();
        if (val) addressParts.push(val);
      });
      const shippingAddress = addressParts.length > 0 ? addressParts.join(', ') : 'Default Address';

      const originalText = this.placeOrderBtn!.innerText;
      this.placeOrderBtn!.innerText = 'Processing Payment...';
      this.placeOrderBtn!.style.pointerEvents = 'none';

      // 1. Get Payment Intent from Backend
      const cartTotal = 458900; // Simulated cents, would fetch real total
      const intentResponse = await fetch('/api/payment/create-intent', {
        method: 'POST',
        headers: { 'Content-Type': 'application/json', 'Authorization': `Bearer ${auth}` },
        body: JSON.stringify({ amount: cartTotal, currency: 'usd' })
      });
      const intentData = await intentResponse.json();

      if (!intentData.success) {
        throw new Error(intentData.message);
      }

      // 2. Confirm Card Payment
      const result = await this.stripe.confirmCardPayment(intentData.data.clientSecret, {
        payment_method: { card: this.card }
      });

      if (result.error) {
        alert(result.error.message);
        this.resetButton(originalText);
        return;
      }

      // 3. Create Order
      const response = await apiClient.createOrder('Stripe', shippingAddress, this.lat ?? undefined, this.lng ?? undefined);
      
      if (response.success) {
        alert('Order placed successfully!');
        window.location.href = '/swiftcart-html/index.html';
      } else {
        alert('Failed to place order: ' + response.message);
        this.resetButton(originalText);
      }
    } catch (error) {
      console.error('Checkout error:', error);
      alert('An error occurred while placing your order.');
      this.resetButton(this.placeOrderBtn!.innerText.replace('Processing Payment...', 'Place Order'));
    }
  }

  private resetButton(text: string) {
    if (this.placeOrderBtn) {
      this.placeOrderBtn.innerText = text;
      this.placeOrderBtn.style.pointerEvents = 'auto';
    }
  }
}
