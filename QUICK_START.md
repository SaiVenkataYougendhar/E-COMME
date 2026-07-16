# Quick Start Guide - SwiftCart Full-Stack

## What's Been Created

Your e-commerce project now has:
- ✓ Spring Boot backend with REST API (Java 17)
- ✓ MySQL database with complete schema
- ✓ JWT authentication system
- ✓ TypeScript frontend components with UI/UX
- ✓ Product catalog system
- ✓ Shopping cart management
- ✓ Order processing
- ✓ User profiles
- ✓ Spring Data JPA for database

## 5-Minute Setup

### 1. Backend Setup (2 minutes)

```bash
# Navigate to backend
cd swiftcart-backend

# Configure database in src/main/resources/application.properties
# Edit: spring.datasource.username and password

# Create database
mysql -u root -p < ../database.sql

# Build project
mvn clean install

# Start development server
mvn spring-boot:run
```

Backend will run at `http://localhost:8080/api`

### 2. Frontend Setup (1 minute)

```bash
# Navigate to frontend
cd frontend

# Install dependencies
npm install

# Build TypeScript
npm run build

# For development with hot reload
npm run dev
```

Frontend dev server will run at `http://localhost:3000`

### 3. HTML Template (2 minutes)

```bash
# The swiftcart-html folder already has everything
# Just add the bundle script to your HTML files
# (See INTEGRATION_GUIDE.md for details)
```

## First Test

1. Backend is running: Visit `http://localhost:5000/api/health`
2. Check Products API: Visit `http://localhost:5000/api/products`
3. Open HTML: Visit `http://localhost:8000/swiftcart-html/index.html`

## Database Setup

Make sure MySQL is running, then:

```bash
# Windows
mysql -u root -p < backend\database.sql

# Mac/Linux
mysql -u root -p < backend/database.sql
```

Tables created:
- users
- products
- cart
- orders
- order_items
- reviews
- wishlist

## Key Commands

### Backend
```bash
npm run dev      # Development server with hot reload
npm run build    # Compile TypeScript
npm start        # Run production build
```

### Frontend
```bash
npm run dev      # Development server
npm run build    # Production build
npm run compile  # TypeScript compilation only
```

## Project Structure

```
electronics/
├── backend/           # REST API (Express + TypeScript)
│   └── API runs on port 5000
├── frontend/          # UI Components (TypeScript)
│   └── Dev server on port 3000
├── swiftcart-html/    # Existing HTML template
│   └── Served on port 8000
├── README.md          # Full documentation
└── INTEGRATION_GUIDE.md # How to connect frontend to HTML
```

## Testing the API

### Using curl:

```bash
# Get all products
curl http://localhost:5000/api/products

# Search products
curl "http://localhost:5000/api/products/search?keyword=laptop"

# Register user
curl -X POST http://localhost:5000/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{"email":"test@example.com","password":"pass123","first_name":"John","last_name":"Doe","phone":"1234567890"}'
```

### Using Postman:

1. Import the API endpoints
2. Set base URL to `http://localhost:5000/api`
3. Test endpoints one by one

## Environment Variables

### Backend (.env)
```
PORT=5000
DB_HOST=localhost
DB_USER=root
DB_PASSWORD=your_password
DB_NAME=swiftcart_db
JWT_SECRET=your_secret_key
```

### Frontend (.env)
```
API_URL=http://localhost:5000/api
```

## Common Tasks

### Add a New Product
```bash
curl -X POST http://localhost:5000/api/products \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "name":"Laptop",
    "description":"High-performance laptop",
    "price":999.99,
    "category":"Electronics",
    "stock":10
  }'
```

### Get User Profile
```bash
curl http://localhost:5000/api/auth/profile \
  -H "Authorization: Bearer YOUR_TOKEN"
```

### Create Order
```bash
curl -X POST http://localhost:5000/api/orders \
  -H "Content-Type: application/json" \
  -H "Authorization: Bearer YOUR_TOKEN" \
  -d '{
    "payment_method":"credit_card",
    "shipping_address":"123 Main St, City, State 12345"
  }'
```

## Troubleshooting

### MySQL Connection Error
```
Error: connect ECONNREFUSED 127.0.0.1:3306
```
**Fix**: Start MySQL service
- Windows: `net start MySQL80`
- Mac: `brew services start mysql-community-server`
- Linux: `sudo systemctl start mysql`

### Port Already in Use
```
Error: listen EADDRINUSE: address already in use :::5000
```
**Fix**: Kill process on that port or change PORT in .env

### CORS Error
```
Access to XMLHttpRequest blocked by CORS policy
```
**Fix**: Update CORS_ORIGIN in backend .env to match frontend URL

### Module not found
```
Error: Cannot find module
```
**Fix**: Run `npm install` in both backend and frontend

## Next: Integration

Once everything is working:

1. Read [INTEGRATION_GUIDE.md](INTEGRATION_GUIDE.md)
2. Add bundle script to HTML files
3. Update HTML structure as needed
4. Test frontend components

## Frontend Components Available

### ProductPage
```typescript
import { ProductPage } from './pages/ProductPage';

const page = new ProductPage('container-id');
await page.loadProducts();
await page.searchProducts('keyword');
await page.loadByCategory('category-name');
```

### CartManager
```typescript
import { CartManager } from './components/CartManager';

const manager = new CartManager(containerElement);
await manager.loadCart();
const total = manager.getCartTotal();
```

### AuthManager
```typescript
import AuthManager from './components/AuthManager';

await AuthManager.login(email, password);
if (AuthManager.isLoggedIn()) { /* ... */ }
AuthManager.logout();
```

## Useful Links

- [REST API Documentation](./README.md#api-endpoints)
- [Integration Guide](./INTEGRATION_GUIDE.md)
- [Database Schema](./README.md#database-schema)
- [TypeScript Components](./README.md#typescript-components)

## API Health Check

Open browser and visit: `http://localhost:5000/api/health`

You should see:
```json
{
  "status": "API is running",
  "timestamp": "2026-07-14T..."
}
```

## Support

Check logs in these places:
1. **Backend console**: `npm run dev` terminal
2. **Browser console**: F12 → Console tab
3. **Network tab**: Check API requests and responses
4. **Terminal errors**: Read error messages carefully

## Success Checklist

- [ ] Backend npm packages installed
- [ ] Frontend npm packages installed
- [ ] MySQL database created
- [ ] .env file configured
- [ ] Backend running on 5000
- [ ] API health check passes
- [ ] Can get products list
- [ ] Frontend build completes
- [ ] Ready to integrate!

## What's Next?

1. **Setup Complete?** → Read INTEGRATION_GUIDE.md
2. **Want to Add Products?** → Use /api/products endpoint
3. **Need Admin Panel?** → Create admin routes in backend
4. **Want Deployment?** → Check production guides in README.md

Enjoy building! 🚀
