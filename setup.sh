#!/bin/bash
# SwiftCart Full-Stack Setup Script

# Colors for output
RED='\033[0;31m'
GREEN='\033[0;32m'
YELLOW='\033[1;33m'
BLUE='\033[0;34m'
NC='\033[0m' # No Color

# Check if Node.js is installed
echo -e "${BLUE}Checking prerequisites...${NC}"
if ! command -v node &> /dev/null; then
    echo -e "${RED}Node.js is not installed. Please install Node.js first.${NC}"
    exit 1
fi

if ! command -v mysql &> /dev/null; then
    echo -e "${YELLOW}MySQL is not installed. Make sure MySQL server is running.${NC}"
fi

echo -e "${GREEN}✓ Node.js is installed${NC}"
echo ""

# Install backend dependencies
echo -e "${BLUE}Setting up Backend...${NC}"
cd backend
echo "Installing dependencies..."
npm install > /dev/null 2>&1

if [ -f .env ]; then
    echo -e "${GREEN}✓ .env file exists${NC}"
else
    echo -e "${YELLOW}Creating .env from .env.example${NC}"
    cp .env.example .env
    echo -e "${YELLOW}Please edit .env with your database credentials${NC}"
fi

echo -e "${GREEN}✓ Backend setup complete${NC}"
cd ..

# Install frontend dependencies
echo ""
echo -e "${BLUE}Setting up Frontend...${NC}"
cd frontend
echo "Installing dependencies..."
npm install > /dev/null 2>&1
echo -e "${GREEN}✓ Frontend setup complete${NC}"
cd ..

# Display summary
echo ""
echo -e "${BLUE}════════════════════════════════════════════════════════════${NC}"
echo -e "${GREEN}✓ SwiftCart Full-Stack Setup Complete!${NC}"
echo -e "${BLUE}════════════════════════════════════════════════════════════${NC}"
echo ""
echo -e "${BLUE}Next Steps:${NC}"
echo ""
echo -e "${YELLOW}1. Configure Database${NC}"
echo "   • Edit backend/.env with MySQL credentials"
echo "   • Run: mysql -u root -p < backend/database.sql"
echo ""
echo -e "${YELLOW}2. Start Backend${NC}"
echo "   • cd backend && npm run dev"
echo "   • API will run on http://localhost:5000"
echo ""
echo -e "${YELLOW}3. Start Frontend${NC}"
echo "   • cd frontend && npm run dev"
echo "   • Dev server on http://localhost:3000"
echo ""
echo -e "${YELLOW}4. Run HTML Template${NC}"
echo "   • python -m http.server 8000 (from electronics folder)"
echo "   • Open http://localhost:8000/swiftcart-html/"
echo ""
echo -e "${BLUE}Documentation:${NC}"
echo "   • README.md - Full documentation"
echo "   • QUICK_START.md - Get running in 5 minutes"
echo "   • INTEGRATION_GUIDE.md - Connect frontend to HTML"
echo "   • ARCHITECTURE.md - System design details"
echo ""
echo -e "${GREEN}Happy coding! 🚀${NC}"
