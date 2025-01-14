# E-Commerce Application

## Overview
This project is a full-stack e-commerce application built with **Java Spring Boot** and **Thymeleaf**. It showcases core CRUD operations, JWT-based authentication, and an admin variant for enhanced functionality.

## Features

### User Features
- **JWT Authentication**: Secure login and registration for users with JSON Web Tokens.
- **Browse Products**: Users can view available products with dynamic content rendered via Thymeleaf.
- **Add to Cart**: Seamless cart management for selected products.
- **Checkout and Order Management**: Place orders and view order history.

### Admin Features
- **Product Management**: Full CRUD operations to create, read, update, and delete products.
- **Dashboard**: Admin-specific interface for managing the platform.

### Security
- **Role-Based Access Control**: Different features for users and admins using Spring Security.
- **JWT Token Implementation**: Ensures secure access to APIs for both users and admins.

## Tech Stack
- **Backend**: Java Spring Boot
- **Frontend**: Thymeleaf
- **Database**: MySQL
- **Security**: Spring Security with JWT

## Installation
1. Clone the repository:
   ```bash
   git clone https://github.com/your-repo/ecommerce.git
   ```

2. Navigate to the project directory:
   ```bash
   cd ecommerce
   ```

3. Configure the database connection in `application.properties`.

4. Build and run the application:
   ```bash
   mvn spring-boot:run
   ```
