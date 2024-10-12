# Ecommerce-Backend-API-Services-V2

## About
Used Java Spring Boot to build e-commerce backend API services and provide CRUD, product, item, user, log-in, register, etc.  
Skills: JAVA, Spring Boot, JDBC, H2, JUnit, MySQL, REST API, intelliJ IDEA
## Framework

![ecommercebackendapi drawio](https://user-images.githubusercontent.com/78866239/235349955-0e1789a8-1d58-45da-83dc-378f37daabb0.png)


# General Flow

1. **Receive Request:**
   - Get the request from the client and extract the necessary variables from the request body.

2. **Set Variables to DTO:**
   - Map the extracted variables to a Data Transfer Object (DTO), which stores the necessary data for the DAO (SQL) operations.

3. **Pass DTO to Service Layer:**
   - Pass the DTO to the Service layer.
   - In the Service layer, apply any required business logic, such as conditional expressions, calculations, or validations.

4. **Pass Data to DAO:**
   - Pass the DTO or other relevant variables to the DAO (Data Access Object).
   - The DAO is responsible for executing SQL queries.

5. **SQL Execution in DAO:**
   - If the SQL query returns multiple rows, use a RowMapper to map the ResultSet to a model object.
   - Then, pass the mapped result to the `namedParameterJdbcTemplate` and return the result as a List.

6. **(Optional) Paginate Results:**
   - If pagination is required, set the List into a `Page` object.

7. **Return Response to Frontend:**
   - Return the response to the frontend using `ResponseEntity.status(HttpStatus.OK).body()`, with the appropriate body content.


## Product
- Query product lists
- Create/Read/Update/Delete(CRUD) products

## User
- Register
- Login

## Item
- Order items
- Query order lists

## Unit Test
- H2 Database


## Database (MySQL)(JDBC)
### Table
### product 
| column name | data type |
|:--------:|:--------:|
| product_id | INT |
|product_name|VARCHAR(128)|
|category|VARCHAR(32)|
|image_url|VARCHAR(256)|
|price|INT|
|stock|INT|
|description|VARCHAR(1024)|
|created_date|TIMESTAMP|
|last_modified_date|TIMESTAMP|

### user
| column name | data type |
|:--------:|:--------:|
|user_id|INT|
|email|VARCHAR(256)|
|password|VARCHAR(256)|
|created_date|TIMESTAMP|
|last_modified_date|TIMESTAMP|
 
### order
| column name | data type |
|:--------:|:--------:|
|order_id|INT|
|user_id|INT|
|total_amount|INT|
|created_date|TIMESTAMP|
|last_modified_date|TIMESTAMP|
 
### order_item
| field name | data type |
|:--------:|:--------:|
|order_item_id|INT|
|order_id|INT|
|product_id|INT|
|quantity|INT|
|amount|INT|
