# SwiftCart Architecture Overview

## System Architecture

```
┌─────────────────────────────────────────────────────────────────┐
│                        CLIENT LAYER                               │
│  ┌────────────────────────────────────────────────────────────┐  │
│  │   Browser (swiftcart-html) - Port 8000                     │  │
│  │  ├─ HTML Templates (index, shop, cart, checkout)           │  │
│  │  ├─ Bootstrap Assets                                       │  │
│  │  └─ bundle.js (TypeScript compiled)                        │  │
│  └────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              ↓ HTTP/REST
┌─────────────────────────────────────────────────────────────────┐
│                      API LAYER                                    │
│  ┌────────────────────────────────────────────────────────────┐  │
│  │   Express.js Server - Port 5000                            │  │
│  │  ├─ Routes                                                 │  │
│  │  │  ├─ /api/auth (register, login, profile)               │  │
│  │  │  ├─ /api/products (CRUD)                               │  │
│  │  │  ├─ /api/cart (add, remove, update)                    │  │
│  │  │  └─ /api/orders (create, list, details)                │  │
│  │  ├─ Controllers (business logic)                           │  │
│  │  ├─ Middleware (auth, error handling, CORS)               │  │
│  │  └─ Models (data queries)                                 │  │
│  └────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
                              ↓ SQL
┌─────────────────────────────────────────────────────────────────┐
│                    DATA LAYER                                     │
│  ┌────────────────────────────────────────────────────────────┐  │
│  │   MySQL Database (swiftcart_db)                            │  │
│  │  ├─ users table          (authentication)                  │  │
│  │  ├─ products table       (catalog)                         │  │
│  │  ├─ cart table           (shopping cart)                   │  │
│  │  ├─ orders table         (order headers)                   │  │
│  │  ├─ order_items table    (order details)                   │  │
│  │  ├─ reviews table        (product reviews)                 │  │
│  │  └─ wishlist table       (saved items)                     │  │
│  └────────────────────────────────────────────────────────────┘  │
└─────────────────────────────────────────────────────────────────┘
```

## Frontend Architecture

```
frontend/
├── src/
│   ├── index.ts                    # Entry point
│   │   └─ Initializes components based on page
│   │
│   ├── types/
│   │   └─ api.ts                   # TypeScript interfaces
│   │      ├─ Product interface
│   │      ├─ User interface
│   │      ├─ CartItem interface
│   │      └─ Order interface
│   │
│   ├── services/
│   │   └─ apiClient.ts             # API communication
│   │      ├─ Auth endpoints
│   │      ├─ Product endpoints
│   │      ├─ Cart endpoints
│   │      └─ Order endpoints
│   │
│   ├── components/
│   │   ├─ ProductCard.ts           # Single product display
│   │   │  ├─ render(): render product card
│   │   │  └─ handleAddToCart(): add to cart handler
│   │   │
│   │   ├─ CartManager.ts           # Cart management
│   │   │  ├─ loadCart(): fetch cart items
│   │   │  ├─ updateQuantity(): modify quantity
│   │   │  ├─ removeItem(): delete from cart
│   │   │  └─ render(): display cart
│   │   │
│   │   └─ AuthManager.ts           # Authentication
│   │      ├─ login(): user login
│   │      ├─ register(): user registration
│   │      ├─ logout(): user logout
│   │      └─ isLoggedIn(): check auth status
│   │
│   ├── pages/
│   │   └─ ProductPage.ts           # Product listing
│   │      ├─ loadProducts(): fetch all products
│   │      ├─ searchProducts(): search function
│   │      ├─ loadByCategory(): filter by category
│   │      └─ renderProducts(): display grid
│   │
│   └─ styles/
│       └─ components.css           # Component styling
│
├── webpack.config.js               # Build configuration
├── tsconfig.json                   # TypeScript configuration
└── package.json                    # Dependencies

Data Flow:
HTML → TypeScript Component → API Client → Express API → MySQL
      ↓                        ↓
    Bundle.js            JWT Token
```

## Backend Architecture

```
backend/
├── src/
│   ├── server.ts                   # Express app setup
│   │   └─ Initializes routes and middleware
│   │
│   ├── config/
│   │   └─ database.ts              # MySQL connection
│   │      ├─ pool: connection pool
│   │      ├─ getConnection(): get DB connection
│   │      └─ query(): execute SQL query
│   │
│   ├── models/
│   │   ├─ Product.ts               # Product queries
│   │   │  ├─ getAll()
│   │   │  ├─ getById()
│   │   │  ├─ create()
│   │   │  ├─ update()
│   │   │  └─ delete()
│   │   │
│   │   ├─ User.ts                  # User queries
│   │   │  ├─ getById()
│   │   │  ├─ getByEmail()
│   │   │  ├─ create()
│   │   │  └─ update()
│   │   │
│   │   ├─ Cart.ts                  # Cart queries
│   │   │  ├─ getByUserId()
│   │   │  ├─ addItem()
│   │   │  ├─ updateItem()
│   │   │  ├─ removeItem()
│   │   │  └─ clearCart()
│   │   │
│   │   └─ Order.ts                 # Order queries
│   │      ├─ getById()
│   │      ├─ getByUserId()
│   │      ├─ create()
│   │      ├─ addItem()
│   │      ├─ getItems()
│   │      └─ updateStatus()
│   │
│   ├── controllers/
│   │   ├─ productController.ts     # Product handlers
│   │   ├─ authController.ts        # Auth handlers
│   │   ├─ cartController.ts        # Cart handlers
│   │   └─ orderController.ts       # Order handlers
│   │
│   ├── routes/
│   │   ├─ productRoutes.ts         # /api/products
│   │   ├─ authRoutes.ts            # /api/auth
│   │   ├─ cartRoutes.ts            # /api/cart
│   │   └─ orderRoutes.ts           # /api/orders
│   │
│   └─ middleware/
│       ├─ auth.ts                  # JWT verification
│       │  ├─ authMiddleware()
│       │  └─ adminMiddleware()
│       └─ errorHandler.ts          # Error handling
│
├── database.sql                    # Schema creation
├── .env                            # Configuration
├── package.json                    # Dependencies
└── tsconfig.json                   # TypeScript config

Request Flow:
Client → Route → Middleware → Controller → Model → Database
         ↓                   ↓
      Express            bcrypt/JWT
```

## Authentication Flow

```
┌─────────────────────┐
│   User Registration  │
└──────────┬──────────┘
           │
           ↓
┌─────────────────────────────────────────────┐
│ POST /api/auth/register                      │
│ { email, password, firstName, lastName }     │
└──────────┬──────────────────────────────────┘
           │
           ↓
┌─────────────────────────────────────────────┐
│ authController.register()                    │
│ • Check if email exists                     │
│ • Hash password with bcryptjs               │
│ • Insert into users table                   │
└──────────┬──────────────────────────────────┘
           │
           ↓
    Return Success
           │
           ↓ (User logs in)
           │
┌─────────────────────┐
│   User Login         │
└──────────┬──────────┘
           │
           ↓
┌─────────────────────────────────────────────┐
│ POST /api/auth/login                         │
│ { email, password }                          │
└──────────┬──────────────────────────────────┘
           │
           ↓
┌─────────────────────────────────────────────┐
│ authController.login()                       │
│ • Find user by email                         │
│ • Compare password hash                      │
│ • Create JWT token                           │
│ • Store token in localStorage                │
└──────────┬──────────────────────────────────┘
           │
           ↓
    Return JWT Token
           │
           ↓ (API calls)
           │
┌─────────────────────────────────────────────┐
│ Any Protected Route                          │
│ Authorization: Bearer {JWT_TOKEN}            │
└──────────┬──────────────────────────────────┘
           │
           ↓
┌─────────────────────────────────────────────┐
│ authMiddleware()                             │
│ • Extract JWT from header                   │
│ • Verify JWT signature                      │
│ • Extract user ID from token                │
│ • Attach to request object                  │
└──────────┬──────────────────────────────────┘
           │
           ↓
   Proceed to Controller
```

## Shopping Cart Flow

```
┌──────────────────────┐
│ Browse Products      │
└──────────┬───────────┘
           │
           ↓
┌─────────────────────────────────────┐
│ User clicks "Add to Cart"            │
│ • Requires authentication            │
│ • ProductCard component              │
└──────────┬────────────────────────────┘
           │
           ↓
┌─────────────────────────────────────────────┐
│ POST /api/cart/add                           │
│ { productId, quantity }                      │
│ + JWT Token                                  │
└──────────┬──────────────────────────────────┘
           │
           ↓
┌─────────────────────────────────────────────┐
│ cartController.addToCart()                   │
│ • Get user ID from token                    │
│ • Check if product in cart                  │
│ • If yes: update quantity                   │
│ • If no: insert new row                     │
│ • Update cart table                         │
└──────────┬──────────────────────────────────┘
           │
           ↓
    Item Added to Cart
           │
           ↓ (View cart)
           │
┌─────────────────────────────────────────────┐
│ GET /api/cart                                │
│ + JWT Token                                  │
└──────────┬──────────────────────────────────┘
           │
           ↓
┌─────────────────────────────────────────────┐
│ cartController.getCart()                     │
│ • Get user ID from token                    │
│ • SELECT * FROM cart WHERE user_id = X      │
│ • JOIN with products table                  │
│ • Return cart items with details            │
└──────────┬──────────────────────────────────┘
           │
           ↓
    CartManager renders cart UI
           │
           ├─ Update Quantity → PATCH /api/cart/:id
           ├─ Remove Item    → DELETE /api/cart/:id
           └─ Checkout       → POST /api/orders
```

## Order Processing Flow

```
┌────────────────────────┐
│ User in Checkout Page  │
└──────────┬─────────────┘
           │
           ↓
┌──────────────────────────────────────────────┐
│ Fills shipping info & payment method         │
│ Clicks "Place Order"                         │
└──────────┬─────────────────────────────────────┘
           │
           ↓
┌──────────────────────────────────────────────┐
│ POST /api/orders                              │
│ {                                             │
│   payment_method: "credit_card",             │
│   shipping_address: "123 Main St..."         │
│ }                                             │
│ + JWT Token                                   │
└──────────┬─────────────────────────────────────┘
           │
           ↓
┌──────────────────────────────────────────────┐
│ orderController.createOrder()                 │
│ 1. Get user ID from token                    │
│ 2. Fetch all cart items for user             │
│ 3. Calculate total amount                    │
│ 4. Create order row (status='pending')       │
│ 5. For each cart item:                       │
│    • Insert into order_items table           │
│ 6. Clear user's cart                         │
└──────────┬─────────────────────────────────────┘
           │
           ↓
    Order Created Successfully
           │
           ↓ (Later)
           │
┌──────────────────────────────────────────────┐
│ Admin updates order status                    │
│ PATCH /api/orders/:id/status                 │
│ { status: "shipped" }                        │
└──────────┬─────────────────────────────────────┘
           │
           ↓
    Order status updated in database
```

## Database Relationships

```
users (1) ─→ (many) cart
           ├─→ (many) orders
           ├─→ (many) reviews
           └─→ (many) wishlist

products (1) ─→ (many) cart
           ├─→ (many) reviews
           └─→ (many) wishlist

orders (1) ─→ (many) order_items

order_items (many) ─→ (1) products
```

## Security Features

1. **Authentication**
   - JWT tokens with expiration
   - Token stored in localStorage
   - Verified on protected routes

2. **Authorization**
   - Role-based access (customer, vendor, admin)
   - Middleware checks permissions

3. **Data Protection**
   - Passwords hashed with bcryptjs
   - SQL queries use parameterized statements
   - Input validation

4. **CORS**
   - Frontend CORS origin configured
   - Credentials allowed

## Error Handling

```
Client Error        Backend Error         Response
│                   │                      │
├─ Missing token → authMiddleware fails  → 401 Unauthorized
├─ Invalid JWT   → jwt.verify fails      → 401 Invalid token
├─ Bad data      → validation fails      → 400 Bad Request
├─ User not found→ getById returns null  → 404 Not Found
└─ DB error      → connection fails      → 500 Server Error
```

## Performance Considerations

1. **Database**
   - Connection pooling (max 10 connections)
   - Indexes on frequently queried columns
   - Parameterized queries prevent SQL injection

2. **API**
   - JWT token verification for auth
   - Middleware error handling
   - CORS headers set once

3. **Frontend**
   - Lazy loading of products
   - LocalStorage for token
   - Event delegation for buttons

## Scaling Opportunities

1. **Caching**
   - Redis for session management
   - Cache products list

2. **Search**
   - Elasticsearch for product search
   - Advanced filtering

3. **Payment**
   - Stripe integration
   - Payment webhook handling

4. **Notifications**
   - Email service integration
   - Order status notifications

5. **Analytics**
   - User behavior tracking
   - Sales reporting

