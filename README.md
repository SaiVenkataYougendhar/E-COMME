# SwiftCart Full-Stack E-commerce Project

A complete e-commerce solution with Spring Boot backend, MySQL database, and TypeScript frontend with modern UI/UX.

## Project Structure

```
electronics/
├── swiftcart-backend/           # Spring Boot API (Java 17)
│   ├── src/main/java/com/swiftcart/
│   │   ├── SwiftcartBackendApplication.java     # Main application
│   │   ├── controller/                          # REST controllers
│   │   ├── service/                             # Business logic
│   │   ├── entity/                              # JPA entities
│   │   ├── repository/                          # Spring Data JPA repositories
│   │   ├── dto/                                 # Data Transfer Objects
│   │   ├── config/                              # Spring configuration
│   │   ├── security/                            # JWT & Security
│   │   └── exception/                           # Exception handling
│   ├── src/main/resources/
│   │   └── application.properties               # Configuration
│   └── pom.xml                                  # Maven dependencies
│
├── frontend/               # TypeScript frontend
│   ├── src/
│   │   ├── services/       # API client
│   │   ├── components/     # UI components (ProductCard, CartManager, etc)
│   │   ├── pages/          # Page components
│   │   ├── types/          # TypeScript interfaces
│   │   └── styles/         # CSS styles
│   ├── package.json
│   └── tsconfig.json
│
└── swiftcart-html/         # Original HTML template (Static assets)
```
Java 17 or higher
- MySQL 8.0 or higher
- Maven 3.8+
- Node.js 18+ (for frontend

- Node.js (v18 or higher)
- MySQL (v8 or higher)
- npm or yarn

## Setup Instruction (Spring Boot)

#### Prerequisites Check
```bash
java -version      # Should be 17 or higher
mvn -version       # Should be 3.8 or higher
```

#### Configure Database
Edit `swiftcart-backend/src/main/resources/application.properties`:
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/swiftcart_db
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your_jwt_secret_key_here
```

#### Create Database
```bash
mysql -u root -p < database.sql
```

#### Build and Run Backend
```bash
cd swiftcart-backend

# Build project
mvn clean install

# Run application
mvn spring-boot:run
```

The API will be available at `http://localhost:8080/api

The API will be available at `http://localhost:5000`

### 2. Frontend Setup

#### Install Dependencies
```bash
cd frontend
npm install
```

#### Development Server
```bash
npm run dev
```

#### Build for Production
```bash
npm run build
```

## API Endpoints

### Authentication
- `POST /api/auth/register` - Register new user
- `POST /api/auth/login` - Login user
- `GET /api/auth/profile` - Get user profile (requires auth)
- `PATCH /api/auth/profile` - Update user profile (requires auth)

### Products
- `GET /api/products` - Get all products
- `GET /api/products/:id` - Get product by ID
- `GET /api/products/category/:category` - Get products by category
- `GET /api/products/search?keyword=` - Search products
- `POST /api/products` - Create product (requires auth)
- `PATCH /api/products/:id` - Update product (requires auth)
- `DELETE /api/products/:id` - Delete product (requires auth)

### Cart
- `GET /api/cart` - Get user's cart (requires auth)
- `POST /api/cart/add` - Add item to cart (requires auth)
- `PATCH /api/cart/:cartId` - Update cart item quantity (requires auth)
- `DELETE /api/cart/:cartId` - Remove item from cart (requires auth)
- `DELETE /api/cart` - Clear cart (requires auth)

### Orders
- `POST /api/orders` - Create order (requires auth)
- `GET /api/orders` - Get user's orders (requires auth)
- `GET /api/orders/:id` - Get order details (requires auth)
- `PATCH /api/orders/:id/status` - Update order status (requires auth)

## Database Schema

### Users
- id, email, password, first_name, last_name, phone, address, city, state, zip_code, country, avatar_url, role, created_at, updated_at

### Products
- id, name, description, price, discount_price, category, image_url, stock, rating, created_at, updated_at

### Cart
- id, user_id, product_id, quantity, created_at, updated_at

### Orders
- id, user_id, total_amount, status, payment_method, shipping_address, created_at, updated_at

### Order Items
- id, order_id, product_id, quantity, price, created_at

### Reviews
- id, user_id, product_id, rating, comment, created_at, updated_at

### Wishlist
- id, user_id, product_id, created_at

## TypeScript Components

### API Client (`apiClient.ts`)
Centralized API communication with automatic token management

### ProductCard Component
Display individual product with image, price, rating, and add-to-cart functionality

### CartManager Component
Manage cart items with add, remove, and quantity update features

### AuthManager Component
Handle user authentication and session management

### ProductPage Component
Load and display products with category and search filtering

## Key Features

✓ User authentication with JWT tokens
✓ Product catalog with search and filtering
✓ Shopping cart management
✓ Order processing
✓ User profile management
✓ Responsive design
✓ Type-safe frontend with TypeScript
✓ RESTful API architecture
✓ MySQL database with proper relations

## Development Tips

1. **Authentication**: Tokens are stored in localStorage. API client handles automatic token injection
2. **Error Handling**: Check browser console for detailed error messages
3. **CORS**: Backend is configured for localhost:3000. Update `CORS_ORIGIN` in .env for production
4. **Database**: Run `database.sql` to create all tables
5. **API Testing**: Use Postman or similar tools to test endpoints

## Environment Variables

### Backend (.env)
```
PORT=5000
NODE_ENV=development
DB_HOST=localhost
DB_USER=root
DB_PASSWORD=your_password
DB_NAME=swiftcart_db
DB_PORT=3306
JWT_SECRET=your_jwt_secret_key_here
JWT_EXPIRE=7d
CORS_ORIGIN=http://localhost:3000
```

## Common Issues

### Database Connection Error
- Ensure MySQL is running: `mysql -u root -p`
- Check credentials in .env file
- Verify database exists: `SHOW DATABASES;`

### CORS Error
- Update `CORS_ORIGIN` in .env to match frontend URL
- Restart backend server

### Token Expired
- User will be automatically logged out and redirected to login page
- Clear localStorage if needed: `localStorage.clear()`

## Next Steps

1. Run Spring Boot backend on port 8080
2. Build and run TypeScript frontend
3. Connect frontend components to Spring Boot API
4. Add payment gateway integration (Stripe/PayPal)
5. Implement email notifications
6. Add product reviews and ratings
7. Create admin dashboard
8. Implement wishlist functionality
9. Deploy to production (AWS, Azure, Heroku)

## License

MIT License - Feel free to use this project for personal or commercial use.
