# 🛒 E-Commerce Microservices Architecture

> **IT4020 – Modern Topics in IT | Assignment 2 | SLIIT 2026**

A scalable e-commerce backend built using a microservices approach. Each service is independent, maintains its own database, and communicates through a unified API Gateway.

---

## 🏗️ Architecture Overview

| Component | Port | Description |
|---|---|---|
| **API Gateway** | `9000` | Spring Cloud Gateway — routes all external traffic |
| **Product Service** | `8081` | Manages the product catalog |
| **Order Service** | `8082` | Handles order placement and history |
| **Payment Service** | `8083` | Processes transactions and refunds |
| **Cart Service** | `8084` | Manages user shopping carts |
| **Inventory Service** | `8085` | Tracks stock levels and reservations |
| **Notification Service** | `8086` | Sends alerts and updates |

> Each service has its own dedicated **MySQL database** to ensure loose coupling.

---

## 🛠️ Prerequisites

Ensure the following are installed before running the project:

- Java 17 or higher
- Maven 3.8+
- MySQL Server 8.0+
- Postman (for API testing)

---

## ⚙️ Configuration & Setup

### 1. Database Configuration

By default, services use `root` as both the username and password. If your MySQL credentials differ, update the `application.properties` file in **each** service folder:

**File:** `src/main/resources/application.properties` *(repeat for all 6 services)*
```properties
spring.datasource.url=jdbc:mysql://localhost:3306/your_db_name?createDatabaseIfNotExist=true
spring.datasource.username=your_mysql_username
spring.datasource.password=your_mysql_password
```

> **Note:** The `createDatabaseIfNotExist=true` flag automatically creates the necessary schemas on first run.

### 2. Manual Database Creation (Optional)

If you prefer to create the databases manually, run the following in your MySQL terminal:
```sql
CREATE DATABASE product_db;
CREATE DATABASE order_db;
CREATE DATABASE payment_db;
CREATE DATABASE cart_db;
CREATE DATABASE inventory_db;
CREATE DATABASE notification_db;
```

---

## 🚀 How to Run

### Step 1: Build the Project

Open a terminal in the root directory `ecommerce-microservices/` and run:
```bash
mvn clean install -DskipTests
```

### Step 2: Start the Microservices

Start each service in a **separate terminal window**. Start the **API Gateway last**.
```bash
# 1. Product Service
cd product-service && mvn spring-boot:run

# 2. Order Service
cd order-service && mvn spring-boot:run

# 3. Payment Service
cd payment-service && mvn spring-boot:run

# 4. Cart Service
cd cart-service && mvn spring-boot:run

# 5. Inventory Service
cd inventory-service && mvn spring-boot:run

# 6. Notification Service
cd notification-service && mvn spring-boot:run

# 7. API Gateway (start last)
cd api-gateway && mvn spring-boot:run
```

---

## 📑 API Documentation & Usage

### Unified Access via Gateway (Port 9000)

All requests should be directed through the API Gateway:

| Service | Endpoint Prefix | Example URL |
|---|---|---|
| Product | `/api/products` | `http://localhost:9000/api/products` |
| Order | `/api/orders` | `http://localhost:9000/api/orders` |
| Payment | `/api/payments` | `http://localhost:9000/api/payments` |
| Cart | `/api/cart` | `http://localhost:9000/api/cart/{userId}` |
| Inventory | `/api/inventory` | `http://localhost:9000/api/inventory` |
| Notification | `/api/notifications` | `http://localhost:9000/api/notifications` |

### Swagger UI

Access the interactive API documentation:

- **Product Service:** `http://localhost:8081/swagger-ui.html`
- **API Gateway (Combined):** `http://localhost:9000/swagger-ui.html`

---

## 💡 Key Features

- **Single Entry Point** — The API Gateway handles all routing, so clients only need to know one address.
- **Scalability** — Each service can be scaled independently based on demand.
- **Resilience** — A failure in one service (e.g., Notification) does not affect the rest of the system.
