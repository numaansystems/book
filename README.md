# Budget Book Application

A budget book application with Spring Boot 3 backend and Vue frontend built with Maven.

## Features
- Biennium management (2 fiscal years)
- Category management per agency
- Snappy management with budget codes and decision tracking
- PDF report generation via Jasper

## Build & Run

### Prerequisites
- Java 17+
- Maven 3.8+

### Build All (Backend + Frontend)
```bash
mvn clean package
```

### Run Backend Only
```bash
cd backend
mvn spring-boot:run
```
Backend runs on http://localhost:8080

### Run Frontend Only (development)
```bash
cd frontend
npm install
npm run dev
```
Frontend dev server runs on http://localhost:5173

## Usage
1. Start backend: `cd backend && mvn spring-boot:run`
2. In new terminal, start frontend: `cd frontend && npm run dev`
3. Open http://localhost:5173 for Category Management
4. Navigate to /snappies for Snappy Management

## API Endpoints
- GET `/api/categories/by-agency/{agencyId}` - List categories
- POST `/api/categories?agencyId={id}` - Create category
- GET `/api/budget-codes/by-agency/{agencyId}` - List budget codes
- GET `/api/snappies/by-agency/{agencyId}` - List snappies
- POST `/api/snappies` - Create snappy
- PUT `/api/snappies/{id}` - Update snappy