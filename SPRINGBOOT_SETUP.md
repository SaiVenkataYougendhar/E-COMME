# Spring Boot Backend Setup Guide

## Overview

SwiftCart Backend is built with Spring Boot, Spring Security, JWT authentication, and MySQL database using JPA/Hibernate ORM.

## Prerequisites

- Java 17 or higher
- Maven 3.8+
- MySQL 8.0+
- Git

## Installation Steps

### 1. Clone or Extract Project

```bash
cd swiftcart-backend
```

### 2. Configure Database

Edit `src/main/resources/application.properties`:

```properties
spring.datasource.url=jdbc:mysql://localhost:3306/swiftcart_db
spring.datasource.username=root
spring.datasource.password=your_password
jwt.secret=your_jwt_secret_change_this_in_production
```

### 3. Create Database

```bash
mysql -u root -p < ../database.sql
```

Or using MySQL CLI:

```sql
CREATE DATABASE swiftcart_db;
USE swiftcart_db;
-- Tables will be auto-created by Hibernate
```

### 4. Build Project

```bash
mvn clean install
```

### 5. Run Application

```bash
mvn spring-boot:run
```

Or:

```bash
java -jar target/swiftcart-backend-1.0.0.jar
```

Server will run on: `http://localhost:8080/api`

## Project Structure

```
swiftcart-backend/
├── src/main/
│   ├── java/com/swiftcart/
│   │   ├── SwiftcartBackendApplication.java     # Main application
│   │   ├── config/
│   │   │   ├── SecurityConfig.java              # Spring Security config
│   │   │   └── CorsConfig.java                  # CORS configuration
│   │   ├── controller/
│   │   │   ├── AuthController.java              # Auth endpoints
│   │   │   ├── ProductController.java           # Product endpoints
│   │   │   ├── CartController.java              # Cart endpoints
│   │   │   ├── OrderController.java             # Order endpoints
│   │   │   └── HealthController.java            # Health check
│   │   ├── service/
│   │   │   ├── AuthService.java                 # Auth logic
│   │   │   ├── ProductService.java              # Product logic
│   │   │   ├── CartService.java                 # Cart logic
│   │   │   └── OrderService.java                # Order logic
│   │   ├── entity/
│   │   │   ├── User.java                        # User JPA entity
│   │   │   ├── Product.java                     # Product JPA entity
│   │   │   ├── Cart.java                        # Cart JPA entity
│   │   │   ├── Order.java                       # Order JPA entity
│   │   │   └── OrderItem.java                   # OrderItem JPA entity
│   │   ├── repository/
│   │   │   ├── UserRepository.java              # User JPA repository
│   │   │   ├── ProductRepository.java           # Product JPA repository
│   │   │   ├── CartRepository.java              # Cart JPA repository
│   │   │   ├── OrderRepository.java             # Order JPA repository
│   │   │   └── OrderItemRepository.java         # OrderItem JPA repository
│   │   ├── dto/
│   │   │   ├── RegisterRequest.java             # Registration DTO
│   │   │   ├── LoginRequest.java                # Login DTO
│   │   │   ├── AuthResponse.java                # Auth response DTO
│   │   │   ├── ProductDTO.java                  # Product DTO
│   │   │   ├── CartItemDTO.java                 # Cart item DTO
│   │   │   ├── CartItemRequest.java             # Cart request DTO
│   │   │   ├── CreateOrderRequest.java          # Order creation DTO
│   │   │   └── ApiResponse.java                 # API response wrapper
│   │   └── security/
│   │       ├── JwtTokenProvider.java            # JWT token generation/validation
│   │       ├── JwtAuthenticationFilter.java     # JWT filter
│   │       ├── JwtAuthenticationEntryPoint.java # JWT error handler
│   │       └── CustomUserDetailsService.java    # User details service
│   └── resources/
│       └── application.properties                # Application configuration
├── pom.xml                                      # Maven dependencies
└── README.md                                    # Backend documentation
```

## API Endpoints

Base URL: `http://localhost:8080/api`

### Authentication Endpoints

#### Register User
```
POST /auth/register
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123",
  "firstName": "John",
  "lastName": "Doe",
  "phone": "1234567890"
}

Response: 201 Created
{
  "success": true,
  "message": "Registration successful",
  "data": {
    "token": "jwt_token_here",
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "userId": 1
  }
}
```

#### Login User
```
POST /auth/login
Content-Type: application/json

{
  "email": "user@example.com",
  "password": "password123"
}

Response: 200 OK
{
  "success": true,
  "message": "Login successful",
  "data": {
    "token": "jwt_token_here",
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    "userId": 1
  }
}
```

#### Get Profile
```
GET /auth/profile
Authorization: Bearer {token}

Response: 200 OK
{
  "success": true,
  "message": "Profile retrieved",
  "data": {
    "id": 1,
    "email": "user@example.com",
    "firstName": "John",
    "lastName": "Doe",
    ...
  }
}
```

### Product Endpoints

#### Get All Products
```
GET /products

Response: 200 OK
{
  "success": true,
  "message": "Products retrieved",
  "data": [
    {
      "id": 1,
      "name": "Product Name",
      "price": 99.99,
      "category": "Electronics",
      ...
    }
  ]
}
```

#### Search Products
```
GET /products/search?keyword=laptop

Response: 200 OK
```

#### Get Products by Category
```
GET /products/category/Electronics

Response: 200 OK
```

### Cart Endpoints

#### Get Cart Items
```
GET /cart
Authorization: Bearer {token}

Response: 200 OK
```

#### Add to Cart
```
POST /cart/add
Authorization: Bearer {token}
Content-Type: application/json

{
  "productId": 1,
  "quantity": 2
}
```

#### Update Cart Item
```
PATCH /cart/{cartId}
Authorization: Bearer {token}
Content-Type: application/json

{
  "quantity": 3
}
```

#### Remove from Cart
```
DELETE /cart/{cartId}
Authorization: Bearer {token}
```

### Order Endpoints

#### Create Order
```
POST /orders
Authorization: Bearer {token}
Content-Type: application/json

{
  "paymentMethod": "credit_card",
  "shippingAddress": "123 Main St, City, State 12345"
}
```

#### Get User Orders
```
GET /orders
Authorization: Bearer {token}
```

#### Get Order Details
```
GET /orders/{orderId}
Authorization: Bearer {token}
```

## Technology Stack

- **Framework**: Spring Boot 3.1.0
- **Language**: Java 17
- **Database**: MySQL 8
- **ORM**: JPA/Hibernate
- **Security**: Spring Security + JWT
- **Build Tool**: Maven
- **Database Driver**: MySQL Connector/J

## Key Features

✓ JWT-based authentication
✓ Role-based access control (CUSTOMER, VENDOR, ADMIN)
✓ Product management with search and filtering
✓ Shopping cart with quantity management
✓ Order processing and tracking
✓ User profile management
✓ CORS support for frontend integration
✓ Transactional order creation
✓ Automatic database schema generation

## Configuration Properties

Edit `application.properties` to customize:

```properties
# Port
server.port=8080

# Database
spring.datasource.url=jdbc:mysql://localhost:3306/swiftcart_db
spring.datasource.username=root
spring.datasource.password=

# JWT
jwt.secret=your_secret_key_here
jwt.expiration=604800000

# CORS
cors.allowedOrigins=http://localhost:3000,http://localhost:8000

# Logging
logging.level.com.swiftcart=DEBUG
```

## Development

### Build
```bash
mvn clean install
```

### Run
```bash
mvn spring-boot:run
```

### Package
```bash
mvn clean package
```

### Run JAR
```bash
java -jar target/swiftcart-backend-1.0.0.jar
```

## Troubleshooting

### Connection Refused (localhost:3306)
- Ensure MySQL is running
- Check database credentials in `application.properties`

### Port Already in Use
- Change port in `application.properties`: `server.port=8081`
- Or kill process on port 8080

### Database Not Found
- Run: `mysql -u root -p < database.sql`
- Check MySQL connection credentials

### JWT Token Issues
- Verify token format: `Authorization: Bearer {token}`
- Check token expiration
- Ensure JWT secret is configured

## Testing

### Using curl
```bash
# Register
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email":"test@example.com",
    "password":"pass123",
    "firstName":"John",
    "lastName":"Doe",
    "phone":"1234567890"
  }'

# Login
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email":"test@example.com",
    "password":"pass123"
  }'

# Get Products
curl http://localhost:8080/api/products

# Search Products
curl "http://localhost:8080/api/products/search?keyword=laptop"
```

### Using Postman
1. Import API endpoints
2. Set base URL to `http://localhost:8080/api`
3. Authenticate and test endpoints
4. Use JWT token in Authorization header

## Database Schema

Automatically created by Hibernate. Tables include:
- users
- products
- cart
- orders
- order_items

## Security

- Passwords hashed with BCrypt
- JWT tokens with 7-day expiration
- Role-based authorization
- CORS properly configured
- CSRF protection enabled

## Performance Tips

- Use JPA query optimization
- Index frequently searched columns
- Implement pagination for large datasets
- Cache product catalog
- Use database connection pooling

## Deployment

### Production Build
```bash
mvn clean package -DskipTests
```

### Environment Variables
```bash
export SPRING_DATASOURCE_URL=jdbc:mysql://prod-db:3306/swiftcart_db
export SPRING_DATASOURCE_USERNAME=prod_user
export SPRING_DATASOURCE_PASSWORD=prod_password
export JWT_SECRET=production_secret_key_here
```

## Next Steps

1. Frontend integration with TypeScript
2. Add payment gateway integration
3. Implement email notifications
4. Add admin dashboard
5. Implement product reviews
6. Add wislist functionality
7. Deploy to production (AWS, Azure, Heroku)

