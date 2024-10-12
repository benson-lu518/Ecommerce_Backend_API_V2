# Ecommerce-Backend-API-Services-V2

## Overview
This project is an enhanced version of the original *Ecommerce-Backend-API-Services*. It has been updated to JPA from JDBC and Dockerized for easier deployment. The API provides essential e-commerce functionalities, including:

- **CRUD Operations**: Products, Orders, Users
- **Authentication**: User login and registration
- **Order Management**: Order creation and querying, filtering
- **Product Management**: Product creation, updating, deleting, querying, filtering
  

## Framework

![ecommerce_backedn_api](https://github.com/user-attachments/assets/92feb922-a987-4ccc-a705-742847a3d9bc)


## Entity Relationships
![DB Relationship](https://github.com/user-attachments/assets/1a46ce9e-fa79-431d-b7f3-f43c49281a46)

## Features

- **Java Spring Boot Framework**: Utilizes Spring Boot for efficient and modular backend development.
- **JPA for Database Interaction**: Provides object-relational mapping (ORM) and simplifies database access.
- **MySQL Database**: Use MySQL as the relational database for managing product, order, order_item, and user data.
- **Unit Testing with JUnit**: Comprehensive unit tests to ensure the reliability of the codebase.
- **RESTful API**: Provides RESTful endpoints for client interaction (CRUD).
- **Dockerized Setup**: Simplifies environment setup using Docker for easy deployment and scalability.
- **JUnit**: Testing framework
- **H2 Database**: In-memory database for testing



## Project Structure

- **Controller**: Manages API requests and routes them to appropriate services.
- **Service**: Contains business logic for the API.
- **Repository**: Handles data access through JPA repositories.
- **Entity**: Represents data models (e.g., Product, Order, OrderItem, User).
- **DTO (Data Transfer Objects)**: Simplifies the interaction between the `Controller`, `Service`, and `Repository`.
- **Pagination**: Supports pagination to efficiently respond.
- **ProductCategory**: Provides a way to categorize products using enum.
- **Unit Tests**: Ensures the functionality and reliability of the services.

## Requests

### Product
- **Query All Products**:  
  **Endpoint**:
  `GET /products`  `GET /products?limit=5&offset=1&category=CAR&search=Audi&orderBy=createdDate&sort=asc`
  **Description**: Retrieves all available products in the system. Supports pagination, sorting, searching, and orderby.

- **Query Product by ID**:  
  **Endpoint**: `GET /products/{productId}`  
  **Description**: Retrieves details of a specific product by productId.

- **Update Product**:  
  **Endpoint**: `PUT /products/{productId}`  
  **Description**: Updates an existing product's information based on productId.

- **Delete Product**:  
  **Endpoint**: `DELETE /products/{productId}`  
  **Description**: Deletes a product from the system. 

### Order
- **Create Order**:  
  **Endpoint**: `POST /orders`  
  **Description**: Creates a new order for the user. Includes the list of [{productId, quantity}].

- **Get Orders by User ID**:  
  **Endpoint**: `GET /users/{userId}/orders`  `GET /users/{userId}/orders?offset=1&limit=3)`  
  **Description**: Retrieves all orders placed by a specific user.

### User
- **Register**:  
  **Endpoint**: `POST /users/register`  
  **Description**: Registers a new user with their email, password `{email,password}` . Returns a confirmation message upon success.

- **Login**:  
  **Endpoint**: `POST /users/login`  
  **Description**: To check if the user has been registered with their email, password `{email,password}` .




## Prerequisites

- **Java 11+**
- **Maven**
- **Docker**
- **MySQL**

## Setup and Running Locally

   ```bash
   git clone https://github.com/yourusername/Ecommerce-Backend-API-Services-V2.git
   cd Ecommerce-Backend-API-Services-V2
   mvn clean install
   docker-compose up --build
   ```
