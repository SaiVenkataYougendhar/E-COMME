# Getting Started Guide

Welcome to SwiftCart! This guide will help you get the project up and running in minutes.

## System Requirements

Before starting, ensure you have:

### Required
- **Java 17+** - Download from [oracle.com](https://www.oracle.com/java/technologies/downloads/)
- **Maven 3.8+** - Download from [maven.apache.org](https://maven.apache.org/download.cgi)
- **MySQL 8.0+** - Download from [mysql.com](https://www.mysql.com/downloads/)
- **Node.js 18+** - Download from [nodejs.org](https://nodejs.org/)

### Verify Installation

```bash
# Check Java
java -version

# Check Maven
mvn -version

# Check MySQL
mysql -version

# Check Node.js
node -v
npm -v
```

## 10-Minute Setup

### Step 1: Setup Backend (Spring Boot) - 5 minutes

```bash
# Navigate to backend
cd swiftcart-backend

# Open and edit: src/main/resources/application.properties
# Change these lines:
# spring.datasource.username=root
# spring.datasource.password=YOUR_PASSWORD

# Create database
mysql -u root -p < ../database.sql

# Build project
mvn clean install

# Run backend
mvn spring-boot:run
```

✅ Backend runs at: **http://localhost:8080/api**

### Step 2: Setup Frontend (TypeScript) - 3 minutes

Open a **new terminal** and run:

```bash
# Navigate to frontend
cd frontend

# Install dependencies
npm install

# Build TypeScript
npm run build

# Start dev server
npm run dev
```

✅ Frontend runs at: **http://localhost:3000**

### Step 3: Verify Setup - 2 minutes

Open your browser:

1. Check backend: http://localhost:8080/api/health
2. Check API: http://localhost:8080/api/products
3. Check frontend: http://localhost:3000

## Project Structure Overview

```
electronics/
├── swiftcart-backend/           # Spring Boot API (Java)
│   └── Run: mvn spring-boot:run
│
├── frontend/                    # TypeScript UI
│   └── Run: npm run dev
│
├── swiftcart-html/              # Static HTML templates
│   └── Already ready to use
│
└── database.sql                 # Database schema
    └── Already configured
```

## Common First Tasks

### 1. Check if API is Working

```bash
curl http://localhost:8080/api/health
```

Expected response:
```json
{
  "status": "API is running",
  "timestamp": "2026-07-14T...",
  "application": "SwiftCart Backend"
}
```

### 2. Get All Products

```bash
curl http://localhost:8080/api/products
```

### 3. Register a User

```bash
curl -X POST http://localhost:8080/api/auth/register \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123",
    "firstName": "John",
    "lastName": "Doe",
    "phone": "1234567890"
  }'
```

### 4. Login User

```bash
curl -X POST http://localhost:8080/api/auth/login \
  -H "Content-Type: application/json" \
  -d '{
    "email": "user@example.com",
    "password": "password123"
  }'
```

Copy the `token` from response and use in future requests:

```bash
curl http://localhost:8080/api/auth/profile \
  -H "Authorization: Bearer YOUR_TOKEN_HERE"
```

## Development Workflow

### During Development

**Terminal 1 - Backend:**
```bash
cd swiftcart-backend
mvn spring-boot:run
```

**Terminal 2 - Frontend:**
```bash
cd frontend
npm run dev
```

**Terminal 3 - Browser:**
- Open http://localhost:3000 for frontend
- Or http://localhost:8080/api for backend API

### Hot Reload

- **Backend**: Changes require full rebuild (automatic with DevTools)
- **Frontend**: Changes refresh automatically in browser

## Database Management

### View Database

```bash
mysql -u root -p

> USE swiftcart_db;
> SHOW TABLES;
> SELECT * FROM users;
> SELECT * FROM products;
```

### Reset Database

```bash
# Delete and recreate
mysql -u root -p -e "DROP DATABASE swiftcart_db;"
mysql -u root -p < database.sql
```

## Using Postman for Testing

1. Download [Postman](https://www.postman.com/downloads/)
2. Create new workspace
3. Set collection base URL: `http://localhost:8080/api`
4. Create requests for testing:

**Register Request:**
```
POST /auth/register
Body (JSON): {
  "email": "test@example.com",
  "password": "pass123",
  "firstName": "Test",
  "lastName": "User",
  "phone": "1234567890"
}
```

**Login Request:**
```
POST /auth/login
Body (JSON): {
  "email": "test@example.com",
  "password": "pass123"
}
```

Save the token and add to Authorization header:
```
Authorization: Bearer {token_from_login}
```

## Building for Production

### Backend

```bash
cd swiftcart-backend

# Build JAR file
mvn clean package -DskipTests

# Run JAR
java -jar target/swiftcart-backend-1.0.0.jar
```

### Frontend

```bash
cd frontend

# Build optimized bundle
npm run build

# Output in: dist/ folder
```

## Debugging

### Backend Debugging

Enable debug logs in `application.properties`:

```properties
logging.level.com.swiftcart=DEBUG
logging.level.org.springframework.security=DEBUG
```

Check console output for detailed logs.

### Frontend Debugging

1. Open browser DevTools (F12)
2. Go to Console tab for errors
3. Go to Network tab to see API requests
4. Go to Sources tab to debug TypeScript

### Database Debugging

```bash
mysql -u root -p

# List databases
SHOW DATABASES;

# Select database
USE swiftcart_db;

# List tables
SHOW TABLES;

# Describe table structure
DESCRIBE users;

# Check data
SELECT * FROM products LIMIT 5;
```

## Stopping Services

### Stop Backend
- Press `Ctrl + C` in backend terminal

### Stop Frontend Dev Server
- Press `Ctrl + C` in frontend terminal

### Stop MySQL
```bash
# Windows
net stop MySQL80

# Mac
brew services stop mysql-community-server

# Linux
sudo systemctl stop mysql
```

## Common Issues & Solutions

### Issue: "Java not found"
```
Solution: Install Java 17+ and add to system PATH
```

### Issue: "Port 8080 already in use"
```
Solution: Change port in application.properties
server.port=8081
```

### Issue: "MySQL connection refused"
```
Solution: Start MySQL service:
Windows: net start MySQL80
Mac: brew services start mysql-community-server
Linux: sudo systemctl start mysql
```

### Issue: "Can't find module" (Frontend)
```
Solution: Run npm install in frontend folder
cd frontend
npm install
```

### Issue: "Cannot build JAR"
```
Solution: Ensure Java 17+ is installed
java -version
```

## Next Steps

1. ✅ Setup complete
2. → Explore the API documentation in README.md
3. → Read INTEGRATION_GUIDE.md for frontend connection
4. → Add sample products to database
5. → Test shopping cart functionality
6. → Deploy to production

## File Structure Quick Reference

```
Key files to know:

Backend Config:
swiftcart-backend/src/main/resources/application.properties

Frontend Config:
frontend/src/services/apiClient.ts

Database:
database.sql

Documentation:
README.md
QUICK_START.md
SPRINGBOOT_SETUP.md
INTEGRATION_GUIDE.md
ARCHITECTURE.md
```

## Getting Help

### Documentation
- Read [README.md](README.md) for full documentation
- Check [SPRINGBOOT_SETUP.md](SPRINGBOOT_SETUP.md) for backend details
- See [QUICK_START.md](QUICK_START.md) for quick reference

### External Resources
- [Spring Boot Docs](https://spring.io/projects/spring-boot)
- [MySQL Docs](https://dev.mysql.com/doc/)
- [TypeScript Docs](https://www.typescriptlang.org/docs/)

### Debugging
- Check browser console (F12)
- Check backend logs (Maven console)
- Check MySQL directly

## Learning Path

### Week 1: Setup & Basics
- [ ] Complete this Getting Started guide
- [ ] Get all services running
- [ ] Test API endpoints with Postman
- [ ] Create sample data

### Week 2: Frontend Integration
- [ ] Connect TypeScript components
- [ ] Integrate with HTML templates
- [ ] Test shopping cart
- [ ] Test user authentication

### Week 3: Features
- [ ] Add more products
- [ ] Test order creation
- [ ] Implement search
- [ ] Add filtering

### Week 4: Production
- [ ] Build for production
- [ ] Deploy backend
- [ ] Deploy frontend
- [ ] Monitor and maintain

## Success Checklist

- [ ] Java 17+ installed
- [ ] Maven installed
- [ ] MySQL running
- [ ] Node.js installed
- [ ] Backend runs on 8080
- [ ] Frontend runs on 3000
- [ ] API health check passes
- [ ] Can register and login user
- [ ] Can view products
- [ ] Ready for development!

## Useful Commands

```bash
# Backend
mvn clean install              # Build project
mvn spring-boot:run           # Run dev server
mvn clean package             # Create JAR for production

# Frontend
npm install                   # Install dependencies
npm run dev                   # Run dev server
npm run build                 # Create production build
npm run compile               # Compile TypeScript

# Database
mysql -u root -p              # Connect to MySQL
mysql -u root -p < file.sql   # Import SQL file
```

## Support

If you encounter issues:

1. Check logs in console
2. Review documentation
3. Check external resources
4. Verify all services are running

---

**You're all set! Happy coding! 🚀**

Start with: `mvn spring-boot:run` in backend terminal and `npm run dev` in frontend terminal.

