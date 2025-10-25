# Budget Book Application

A biennium budget book management system built with Spring Boot 3 backend and Vue.js frontend.

## Features

- **Biennium Management**: Manages 2 fiscal years with year-based access control
  - In first year: data can be entered for both years
  - In second year: data can be entered only for second year, first year is read-only

- **Category Management**:
  - Create, update, and delete categories per agency
  - Unique title validation within each agency
  - Track created by and updated by information

- **Snappy Management**:
  - Enter budget data for selected agency/fiscal year/budget code/category
  - Decision status (IN/OUT)
  - Sort order capability
  - Comprehensive financial tracking:
    - Recurring and non-recurring requirements
    - Recurring and non-recurring receipts
    - Recurring and non-recurring FTE (up to 3 decimal places)
  - Separate amounts for Year 1 and Year 2

- **Report Generation**:
  - PDF report generation using Jasper Reports
  - Year-specific templates (year1/ and year2/ folders)
  - Automatic template selection based on current fiscal year

## Tech Stack

- **Backend**: Spring Boot 3, Java 17, Maven
- **Frontend**: Vue.js 3, Vite
- **Database**: H2 (in-memory)
- **Reporting**: Jasper Reports
- **Build**: Maven (builds both frontend and backend in one command)

## Prerequisites

- Java 17 or higher
- Maven 3.6+
- Node.js 18+ and npm (automatically installed by frontend-maven-plugin during build)

## Building the Application

The application can be built with a single Maven command that builds both backend and frontend:

```bash
mvn clean install
```

This will:
1. Install Node.js and npm (via frontend-maven-plugin)
2. Install frontend dependencies
3. Build the Vue.js frontend
4. Copy frontend build to Spring Boot static resources
5. Build the Spring Boot backend
6. Run tests

## Running the Application

After building, run the application:

```bash
mvn spring-boot:run
```

Or run the JAR file:

```bash
java -jar target/budget-book-1.0.0-SNAPSHOT.jar
```

The application will be available at: http://localhost:8080

## Development

### Backend Development

The backend uses Spring Boot 3 with:
- JPA for data persistence
- H2 in-memory database
- REST API controllers
- Validation with Bean Validation

### Frontend Development

For frontend development with hot reload:

```bash
cd frontend
npm install
npm run dev
```

The development server will start at http://localhost:3000 with API proxy to backend.

### Database

The application uses H2 in-memory database with sample data pre-loaded:
- Access H2 console at: http://localhost:8080/h2-console
- JDBC URL: `jdbc:h2:mem:budgetdb`
- Username: `sa`
- Password: (empty)

Sample data includes:
- 3 agencies (Education, Health, Transportation)
- 5 budget codes
- 3 categories
- 1 active biennium (2023-2024)

## API Endpoints

### Agencies
- `GET /api/agencies` - Get all agencies
- `GET /api/agencies/{id}` - Get agency by ID

### Budget Codes
- `GET /api/budget-codes?agencyId={id}` - Get budget codes by agency
- `GET /api/budget-codes/{id}` - Get budget code by ID

### Categories
- `GET /api/categories?agencyId={id}` - Get categories by agency
- `GET /api/categories/{id}` - Get category by ID
- `POST /api/categories` - Create new category
- `PUT /api/categories/{id}` - Update category
- `DELETE /api/categories/{id}` - Delete category

### Snappies
- `GET /api/snappies?agencyId={id}&fiscalYear={year}` - Get snappies
- `GET /api/snappies/{id}` - Get snappy by ID
- `POST /api/snappies` - Create new snappy
- `PUT /api/snappies/{id}` - Update snappy
- `DELETE /api/snappies/{id}` - Delete snappy

### Biennium
- `GET /api/biennium/active` - Get active biennium

## Report Templates

Jasper report templates are located in:
- `src/main/resources/templates/year1/` - Templates for first fiscal year
- `src/main/resources/templates/year2/` - Templates for second fiscal year

The application automatically selects the appropriate template based on the current fiscal year.

## Testing

Run tests:

```bash
mvn test
```

## Project Structure

```
budget-book/
├── src/
│   ├── main/
│   │   ├── java/com/numaansystems/budgetbook/
│   │   │   ├── model/          # Domain entities
│   │   │   ├── repository/     # JPA repositories
│   │   │   ├── service/        # Business logic
│   │   │   ├── controller/     # REST controllers
│   │   │   ├── dto/            # Data transfer objects
│   │   │   └── config/         # Configuration
│   │   └── resources/
│   │       ├── templates/
│   │       │   ├── year1/      # Year 1 report templates
│   │       │   └── year2/      # Year 2 report templates
│   │       ├── application.properties
│   │       └── data.sql        # Sample data
│   └── test/                   # Test classes
├── frontend/
│   ├── src/
│   │   ├── views/             # Vue components/views
│   │   ├── services/          # API services
│   │   ├── App.vue            # Main app component
│   │   └── main.js            # App entry point
│   ├── package.json
│   └── vite.config.js
└── pom.xml                    # Maven configuration
```

## License

Copyright © 2024 Numaan Systems
