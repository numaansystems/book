# Budget Book Application

A comprehensive budget management application built with Spring Boot 3 backend and Vue.js frontend.

## Features

### Core Functionality
- **Biennium Management**: Handles 2-year fiscal periods with intelligent data entry controls
  - In Year 1: Users can enter data for both years
  - In Year 2: Users can only modify Year 2 data (Year 1 becomes read-only)
- **Agency Management**: Multi-agency support with isolated data
- **Category Management**: Create and manage budget categories per agency
- **Snappy Management**: Detailed budget entries with comprehensive financial data
- **PDF Reports**: Generate reports using Jasper Reports with year-specific templates

### Technical Features
- **Spring Boot 3**: Modern Java backend with JPA/Hibernate
- **Vue.js 3**: Reactive frontend with modern JavaScript
- **Maven Multi-module**: Single build process for both frontend and backend
- **H2 Database**: In-memory database for development (easily configurable for production)
- **Jasper Reports**: Professional PDF report generation

## Project Structure

```
book/
├── backend/                           # Spring Boot application
│   ├── src/main/java/com/example/budgetbook/
│   │   ├── model/                     # JPA entities
│   │   │   ├── Agency.java
│   │   │   ├── BudgetCode.java
│   │   │   ├── Category.java
│   │   │   ├── Snappy.java
│   │   │   └── FiscalYear.java
│   │   ├── repository/                # Data access layer
│   │   ├── service/                   # Business logic
│   │   ├── controller/                # REST endpoints
│   │   └── BackendApplication.java
│   └── src/main/resources/
│       ├── application.properties
│       ├── data.sql                   # Sample data
│       └── jrxml/                     # Jasper report templates
│           ├── year1/
│           └── year2/
├── frontend/                          # Vue.js application
│   ├── src/
│   │   ├── pages/
│   │   │   ├── CategoryPage.vue
│   │   │   └── SnappyPage.vue
│   │   └── main.js
│   ├── package.json
│   └── vite.config.js
└── pom.xml                           # Parent Maven configuration
```

## Data Model

### Core Entities

1. **Agency**: Top-level organization
2. **BudgetCode**: Budget classification per agency
3. **Category**: Custom categorization per agency (unique title constraint)
4. **Snappy**: Detailed budget entries with:
   - Title and description
   - Budget code and category associations
   - Decision status (in/out)
   - Sort order
   - Financial amounts for both years:
     - Recurring/Non-recurring Requirements
     - Recurring/Non-recurring Receipts
     - Recurring/Non-recurring FTE (up to 3 decimals)

### Business Rules

- **Biennium Control**: Fiscal year restrictions enforced at service level
- **Category Uniqueness**: Category titles must be unique within each agency
- **Data Integrity**: Referential integrity maintained through JPA relationships

## REST API Endpoints

### Categories
- `POST /api/categories?agencyId={id}` - Create category
- `GET /api/categories?agencyId={id}` - List categories by agency

### Budget Codes
- `GET /api/budget-codes?agencyId={id}` - List budget codes by agency

### Snappies
- `POST /api/snappies?agencyId={id}&requestYear={YEAR1|YEAR2}` - Create/update snappy
- `GET /api/snappies` - List all snappies

### Reports
- `GET /api/reports/pdf?template={name}&year={year1|year2}` - Generate PDF report

## Build & Run

### Prerequisites
- Java 17+
- Node.js 18+ (automatically downloaded by Maven)
- Maven 3.6+

### Build
```bash
# Build entire project (backend + frontend)
mvn clean install

# Build specific module
mvn clean install -pl backend
mvn clean install -pl frontend
```

### Run Backend
```bash
cd backend
mvn spring-boot:run
```

Backend will start on `http://localhost:8080`

### Run Frontend (Development)
```bash
cd frontend
npm run dev
```

Frontend development server will start on `http://localhost:5173`

### Run Frontend (Production Build)
The frontend is automatically built and bundled during the Maven build process.

## Configuration

### Application Settings
Edit `backend/src/main/resources/application.properties`:

```properties
# Database configuration
spring.datasource.url=jdbc:h2:mem:bookdb
spring.datasource.username=sa
spring.datasource.password=

# Business configuration
app.activeFiscalYear=YEAR1  # or YEAR2
```

### Sample Data
Sample agencies and budget codes are automatically loaded from `data.sql`.

## Report Templates

Jasper report templates are organized by fiscal year:
- `src/main/resources/jrxml/year1/` - Year 1 templates
- `src/main/resources/jrxml/year2/` - Year 2 templates

The system automatically selects the appropriate template based on the requested year.

## Development Notes

### Adding New Report Templates
1. Create `.jrxml` files in the appropriate year folder
2. Use the template name in the PDF API endpoint
3. Templates are compiled at runtime

### Database Console
Access H2 console at `http://localhost:8080/h2-console` with:
- JDBC URL: `jdbc:h2:mem:bookdb`
- Username: `sa`
- Password: (empty)

### Frontend Development
- Vue 3 with Composition API
- Vite for fast development builds
- Axios for HTTP requests
- Vue Router for navigation

## Production Deployment

### Database
Replace H2 configuration with production database:
```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/bookdb
spring.datasource.username=username
spring.datasource.password=password
spring.jpa.hibernate.ddl-auto=validate
```

### Frontend
The production build creates optimized assets in `frontend/dist/` which can be served by any web server or integrated with the Spring Boot application.

## Contributing

1. Ensure all tests pass: `mvn test`
2. Follow existing code patterns
3. Update this README for significant changes
4. Consider business rules for biennium behavior when modifying data entry logic

## License

[Your License Here]