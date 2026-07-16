import { CartManager } from './components/CartManager';
import { AuthManager } from './components/AuthManager';
import { ProductPage } from './pages/ProductPage';
import { CheckoutManager } from './components/CheckoutManager';
import './styles/components.css';

// Initialize components when DOM is loaded
document.addEventListener('DOMContentLoaded', async () => {
  // Check if we're on different pages and initialize accordingly
  const currentPath = window.location.pathname;

  if (currentPath.includes('shop.html') || currentPath.includes('index.html')) {
    // Initialize product listing
    const productContainer = document.getElementById('products-container');
    if (productContainer) {
      const productPage = new ProductPage('products-container');
      productPage.initUIFilters();
      await productPage.loadProducts();

      // Setup search functionality
      const searchBtn = document.querySelector('button[type="submit"]') as HTMLElement;
      const searchInput = document.querySelector('input[type="search"]') as HTMLInputElement;

      searchBtn?.addEventListener('click', async (e) => {
        e.preventDefault();
        if (searchInput?.value) {
          await productPage.searchProducts(searchInput.value);
        }
      });
    }
  }

  if (currentPath.includes('cart.html')) {
    // Initialize cart
    const cartContainer = document.querySelector('.cart-container') || 
                          document.querySelector('[data-cart-container]') ||
                          document.querySelector('main');
    
    if (cartContainer) {
      const cartManager = new CartManager(cartContainer as HTMLElement);
      await cartManager.loadCart();
    }
  }

  if (currentPath.includes('checkout.html')) {
    const checkoutContainer = document.querySelector('.checkout-area');
    if (checkoutContainer) {
      new CheckoutManager(checkoutContainer as HTMLElement);
    }
  }

  // Setup authentication UI
  setupAuthUI();
});

// Setup authentication UI
function setupAuthUI(): void {
  const authToken = localStorage.getItem('auth_token');
  const profileBtn = document.querySelector('[data-profile-btn]') as HTMLElement;
  const loginBtn = document.querySelector('[data-login-btn]') as HTMLElement;
  const logoutBtn = document.querySelector('[data-logout-btn]') as HTMLElement;

  if (authToken) {
    // User is logged in
    if (profileBtn) profileBtn.style.display = 'block';
    if (loginBtn) loginBtn.style.display = 'none';
    if (logoutBtn) logoutBtn.style.display = 'block';

    logoutBtn?.addEventListener('click', () => {
      AuthManager.getInstance().logout();
      localStorage.clear();
      window.location.reload();
    });
  } else {
    // User is not logged in
    if (profileBtn) profileBtn.style.display = 'none';
    if (loginBtn) loginBtn.style.display = 'block';
    if (logoutBtn) logoutBtn.style.display = 'none';

    loginBtn?.addEventListener('click', () => {
      window.location.href = '/swiftcart-html/';
    });
  }
}

export { CartManager, AuthManager, ProductPage };
