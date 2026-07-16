# Integration Guide: Connecting Frontend to Backend

This guide explains how to integrate the TypeScript frontend components with the existing HTML template.

## Step 1: Include TypeScript Bundle in HTML

Add the compiled TypeScript bundle to your HTML files:

```html
<!-- At the end of body in index.html, cart.html, shop.html -->
<script src="/dist/bundle.js"></script>
```

Or if using the dev server:
```html
<script src="http://localhost:3000/dist/bundle.js"></script>
```

## Step 2: Update HTML Elements

### Product Container
Replace or modify the products section in `index.html` and `shop.html`:

```html
<!-- Add ID for the component to target -->
<div id="products-container" class="products-container">
  <!-- Products will be inserted here -->
</div>
```

### Cart Container
Update `cart.html` to have a container for the cart manager:

```html
<!-- Add container for cart items -->
<div class="cart-container" data-cart-container>
  <!-- Cart items will be inserted here -->
</div>
```

### Authentication Elements
Add data attributes to buttons for authentication handling:

```html
<!-- Profile/Login buttons in header -->
<a href="#" class="xc-header-one__btn" data-profile-btn>
  <i class="icon-user"></i>Profile
</a>

<button class="btn btn-primary" data-login-btn>Login</button>
<button class="btn btn-danger" data-logout-btn>Logout</button>
```

## Step 3: API Client Configuration

Create `.env` or modify to set API URL:

```javascript
// In apiClient.ts - API_URL defaults to http://localhost:5000/api
baseURL: process.env.API_URL || 'http://localhost:5000/api'
```

## Step 4: Add Event Listeners for Add to Cart

The ProductCard component automatically adds event listeners to buttons with class `add-to-cart-btn`:

```html
<button class="add-to-cart-btn" data-product-id="1">Add to Cart</button>
```

## Step 5: Handle User Authentication

### Login Flow
1. User clicks "Login" button
2. Redirect to login page
3. After successful login, JWT token is stored in localStorage
4. Page redirects to dashboard/home
5. Components automatically detect token and show/hide relevant UI

### Logout Flow
1. User clicks "Logout" button
2. Token is removed from localStorage
3. Page reloads
4. User is returned to login page

## Step 6: Real-time Cart Updates

The CartManager component automatically:
- Loads cart items when initialized
- Updates quantity with live calculation
- Shows/hides checkout button based on cart items
- Displays cart total with shipping

## Step 7: Product Search

Add search functionality to existing search form:

```html
<form action="#">
  <input type="search" placeholder="Search" />
  <select>
    <option value="1" selected disabled>All category</option>
    <option value="2">Category 1</option>
    <option value="3">Category 2</option>
  </select>
  <button type="submit">Search</button>
</form>
```

The component automatically handles the search button click.

## Development Workflow

### Run Backend
```bash
cd backend
npm run dev
```

### Run Frontend Dev Server
```bash
cd frontend
npm run dev
```

### Build Frontend for Production
```bash
cd frontend
npm run build
```

## File Structure After Integration

```
swiftcart-html/
├── index.html           (Updated with product container)
├── shop.html           (Updated with product container)
├── cart.html           (Updated with cart container)
├── checkout.html       (Existing - will auto-update with backend)
├── assets/
│   ├── css/
│   │   ├── swiftcart.css
│   │   └── (component styles will be injected via Webpack)
│   ├── js/
│   │   └── swiftcart.js (Existing)
│   └── img/
├── dist/               (Generated from frontend TypeScript build)
│   ├── bundle.js      (Compiled TypeScript)
│   └── bundle.js.map  (Source maps)
```

## API Integration Examples

### Adding Product Search
```typescript
const productPage = new ProductPage('products-container');

// Load all products
await productPage.loadProducts();

// Search products
await productPage.searchProducts('laptop');

// Filter by category
await productPage.loadByCategory('electronics');
```

### Managing Cart
```typescript
const cartManager = new CartManager(containerElement);

// Load and display cart
await cartManager.loadCart();

// Get cart total
const total = cartManager.getCartTotal();
```

### Authentication
```typescript
import AuthManager from './components/AuthManager';

// Register
await AuthManager.register(email, password, firstName, lastName, phone);

// Login
await AuthManager.login(email, password);

// Check if logged in
if (AuthManager.isLoggedIn()) {
  const user = AuthManager.getCurrentUser();
}

// Logout
AuthManager.logout();
```

## Styling Integration

The component styles are automatically injected via Webpack. To customize:

1. Modify `frontend/src/styles/components.css`
2. Add CSS variables for theming
3. Override Bootstrap classes as needed

## Common Integration Issues

### Issue: Products not loading
- **Solution**: Check browser console for API errors
- Ensure backend is running on port 5000
- Verify CORS is configured correctly

### Issue: Add to cart not working
- **Solution**: Check if user is logged in
- Verify API token is being sent with request
- Check backend logs for API errors

### Issue: Cart items not updating
- **Solution**: Clear browser cache
- Check if cart component is properly initialized
- Verify API endpoint is working

### Issue: Styling looks broken
- **Solution**: Run `npm run build` to rebuild bundle
- Clear browser cache (Ctrl+Shift+Delete)
- Check if bundle.js is properly loaded

## Next Steps

1. **Payment Integration**: Add Stripe/PayPal checkout
2. **Email Notifications**: Send order confirmation emails
3. **Admin Dashboard**: Create admin panel for product management
4. **Product Reviews**: Allow users to review products
5. **Wishlist**: Implement wishlist functionality
6. **Advanced Search**: Add filters by price, rating, etc.
7. **Analytics**: Track user behavior and sales

## Testing

### Manual Testing Checklist
- [ ] Products load on home/shop page
- [ ] Search functionality works
- [ ] Can add items to cart
- [ ] Can update cart quantities
- [ ] Can remove items from cart
- [ ] Cart total calculates correctly
- [ ] Can proceed to checkout
- [ ] Login/Logout works
- [ ] User profile updates
- [ ] Orders are created and displayed

## Deployment

### Deploy Backend (Heroku/Railway/Render)
```bash
# Create Procfile
echo "web: npm start" > Procfile

# Push to Heroku
git push heroku main
```

### Deploy Frontend (Vercel/Netlify)
```bash
# Build optimized bundle
npm run build

# Deploy dist folder to Vercel/Netlify
```

### Environment Variables for Production
```
Backend: DB credentials, JWT_SECRET, CORS_ORIGIN
Frontend: API_URL pointing to production backend
```

## Support

For issues or questions, check:
1. Backend logs: `npm run dev` terminal output
2. Browser console: F12 → Console tab
3. Network tab: Check API requests
4. Database: Verify tables and data

