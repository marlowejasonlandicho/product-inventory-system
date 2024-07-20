# Synopsis

Product Inventory System

# Services available

- **Create Product** : Create a Product

- **List All Products** : Retrieve all Products

- **Get Product** : Retrieves a single Product

- **Update Product** : Updates a single Product

- **Delete Product** : Deletes a single Product

# Installation

Make sure that the following are installed on your machine:

- **Java 17**
- **Maven**

# Testing

There's an existing Unit Test class file named  ```src/test/java/exam/product/ProductInventoryApplicationTests.java``` that you can run the test against the Spring Boot App


# Running the application

  - Maven is required to build OR run the Spring Boot application, Spring Boot application runs on the default port, 8080
  - To build the project, run ```mvn install``` on the project root folder
  - To run the project, either run by ```mvn spring-boot:run``` on the project root folder, OR
  - Run ```java -jar ./target/product-inventory-system-1.0.0.jar``` on the project root folder, OR
  - You can also run the app using Docker. Run ```mvn clean package``` on the project root folder
  - Run ```docker build --tag=product-inventory-system:latest .``` on the project root folder to build the Docker image
  - Run ```docker run -p8080:8080 product-inventory-system``` to run the app from a Docker image
  

# Generic Response message

The Service end-points have a generic response structure contained in a single JSON Object. 
The generic response contains the following attributes:

 - **apiStatus** : App specific verbose status. Please see ApiStatus.java file for more details
 - **message** : Human readable API Response message 
 - **success** : Successful or Failed request boolean indicator
 - **data** : The Service specific response for the given request. JSON Object response varies per Service.
 
**Sample Response from Service:**

```json
{
    "apiStatus": "CREATED",
    "message": "Product Created",
    "success": true,
    "data": <<Could be JSON Object or JSON Array>>
}
```

# Service end-points available

## Create Product

Create a Product

**URL** : `/v1/product`

**Method** : `POST`

**Request BODY attributes** :

- **productName** : Name of Product to be saved in DB
- **productDescription** : Description of Product to be saved in DB
- **productQuantity** : Quantity of Product to be saved in DB
- **productType** : Type of Product to be saved in DB, Could be of: Food, Sports, Household, Music, Electronic, or Appliance
- **unitPrice** : Price of Product to be saved in DB

**Response BODY data attributes** :

- **productId** : An Integer value indicating Unique ID of the Product saved in DB
- **productName** : Name of Product saved in DB
- **productDescription** : Description of Product saved in DB
- **productQuantity** : Quantity of Product saved in DB
- **productType** : Type of Product saved in DB
- **unitPrice** : Price of Product saved in DB

**Request example:**
    
POST /v1/product

```json
{
    "productName": "New Product Name",
    "productDescription": "New Product Description",
    "productQuantity": 3,
    "productType": "Food",
    "unitPrice": 3000.1
}
```

**Response example** :

```json
{
    "apiStatus": "CREATED",
    "message": "Product Created",
    "success": true,
    "data": {
        "productId": 2,
        "productName": "New Product Name",
        "productDescription": "New Product Description",
        "productType": "Food",
        "productQuantity": 3,
        "unitPrice": 3000.1
    }
}
```

## List All Products

Retrieves all Products

**URL** : `/v1/product`

**Method** : `GET`

**Response BODY data attributes** :

List of Products containing the ff attributes:
- **productId** : An Integer value indicating Unique ID of the Product saved in DB
- **productName** : Name of Product saved in DB
- **productDescription** : Description of Product saved in DB
- **productQuantity** : Quantity of Product saved in DB
- **productType** : Type of Product saved in DB
- **unitPrice** : Price of Product saved in DB

**Request example** :

GET /v1/product

**Response example** :

```json
{
    "apiStatus": "FOUND",
    "message": "Product Found",
    "success": true,
    "data": [
        {
            "productId": 1,
            "productName": "New Product Name",
            "productDescription": "New Product Description",
            "productType": "Food",
            "productQuantity": 3,
            "unitPrice": 3000.10
        },
        {
            "productId": 2,
            "productName": "New Product Name",
            "productDescription": "New Product Description",
            "productType": "Food",
            "productQuantity": 3,
            "unitPrice": 3000.10
        }
    ]
}
```

## Get Product

 Retrieves a single Product
 
**URL** : `/v1/product/{productId}`

**Method** : `GET`

**Request Path Variable** :

- **productId** : Product Id from DB


**Response BODY data attributes** :

- **productId** : An Integer value indicating Unique ID of the Product saved in DB
- **productName** : Name of Product saved in DB
- **productDescription** : Description of Product saved in DB
- **productQuantity** : Quantity of Product saved in DB
- **productType** : Type of Product saved in DB
- **unitPrice** : Price of Product saved in DB


**Request example** :

GET /v1/product/1

**Response example** :

```json
{
    "apiStatus": "FOUND",
    "message": "Product Found",
    "success": true,
    "data": {
        "productId": 1,
        "productName": "New Product Name",
        "productDescription": "New Product Description",
        "productType": "Food",
        "productQuantity": 3,
        "unitPrice": 3000.10
    }
}
```

## Update Product

 Updates a single Product, Attributes can be updated individually.
 
**URL** : `/v1/product/{productId}`

**Method** : `PUT`

**Request Path Variable** :

- **productId** : Product Id from DB

**Request BODY attributes** :

- **productName** : Name of Product to be updated in DB
- **productDescription** : Description of Product to be updated in DB
- **productQuantity** : Quantity of Product to be updated in DB
- **productType** : Type of Product to be updated in DB, Could be of: Food, Sports, Household, Music, Electronic, or Appliance
- **unitPrice** : Price of Product to be updated in DB


**Response BODY data attributes** :

- **productId** : An Integer value indicating Unique ID of the Product saved in DB
- **productName** : Name of Product updated in DB
- **productDescription** : Description of Product updated in DB
- **productQuantity** : Quantity of Product updated in DB
- **productType** : Type of Product updated in DB
- **unitPrice** : Price of Product updated in DB


**Request example** :

PUT /v1/product/1

```json
{
    "productName": "Product Name 1",
    "productDescription": "Product Description 1",
    "productQuantity": 20,
    "productType": "Appliance",
    "unitPrice": 200.2
}
```
**Response example**

```json
{
    "apiStatus": "UPDATED",
    "message": "Product Updated",
    "success": true,
    "data": {
        "productId": 1,
        "productName": "Product Name 1",
        "productDescription": "Product Description 1",
        "productType": "Appliance",
        "productQuantity": 20,
        "unitPrice": 200.2
    }
}
```


## Delete Product

 Deletes a single Product
 
**URL** : `/v1/product/{productId}`

**Method** : `DELETE`

**Request Path Variable** :

- **productId** : Product Id from DB


**Response BODY data attributes** :

- **data** : Boolean value indicating a successful deletion of Product


**Request example** :

DELETE /v1/product/1

**Response example** :

```json
{
    "apiStatus": "DELETED",
    "message": "Product Deleted",
    "success": true,
    "data": true
}
```
