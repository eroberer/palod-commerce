# Palod E-Commerce Application Service
This application provides user role based category and product management.

## Installation
1 - Clone this project
```sh
git clone https://github.com/eroberer/palod-commerce.git
cd palod-commerce
```
2 - Install docker-compose (for local db) 

3 - Install maven (for application build)


## Run
1 - Run database
```sh
cd database
docker-compose up
```
2 - Run application
```sh
mvn springboot:run
```

## Database Diagram

![](database/db-diagram.png)


## User Endpoint Authority

-  Admin User
```sh 
Category: find, save, update, delete
Product: find, save, update, delete
```
- Basic User
```sh 
Category: find
Product: find, save (just owner), update (just owner), delete (just owner)
```
- Not Register User
 ```sh 
Category: find
Product: find
```

## Admin User

```json
{
    "email": "admin@admin",
    "password": "password"
}
```

## Some Api Endpoint Example Request / Response

### User
#### POST api/user/register
- Request
 ```json
{
    "fullName": "fatih",
    "email": "test2@test2",
    "password": "password"
}
```
- Response
 HTTP 200 OK

#### POST api/user/login
- Request
 ```json
{
    "email": "test2@test2",
    "password": "password"
}
```
- Response
 HTTP 200 OK
 ```json
{
    "timestamp": "2020-07-23T00:12:44.378149",
    "status": 200,
    "token": "eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJ0ZXN0MkB0ZXN0MiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0JBU0lDIn1dLCJpYXQiOjE1OTU0NTIzNjQsImV4cCI6MTU5NTQ1NTk2NH0.gWV0ykPqJ2KzuZR-8ZIMZp-LUYSre5do_6s_mzOlbAI"
}
```

### Category
#### GET api/category/
- Response
 HTTP 200 OK
 ```json
[
    {
        "id": 1,
        "createdDate": "2020-07-23T00:14:24.888464",
        "name": "Test Category",
        "description": "Test Category description"
    },
    {
        "id": 2,
        "createdDate": "2020-07-23T00:14:43.66807",
        "name": "Test Category 2",
        "description": "Test Category 2 description"
    }
]
```

#### POST api/category/
- Header
```sh
header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE1OTU0NTE3NDgsImV4cCI6MTU5NTQ1NTM0OH0.Q2oOQjXxm2sSY77STxP6iFglI6LZ-qmIQpaEdTK0D0o' 
```
- Request
 ```json
{
    "name" : "Test Category 3",
    "description" : "Test Category 3 description"
}
```
- Response

If user has admin role, will category save and return HTTP 200 OK

If user has not admin role, response http code would be HTTP 403 FORBIDDEN


### Product
#### GET api/product/{productId}
- Response
HTTP 200 OK
 ```json
{
    "id": 2,
    "createdDate": "2020-07-23T00:53:58.664344",
    "name": "Test Product 1",
    "description": null,
    "amount": 133.50,
    "quantity": 22,
    "categoryId": 1,
    "productImageList": []
}
```

#### POST api/product/
- Header
```sh
header 'Authorization: Bearer eyJhbGciOiJIUzI1NiJ9.eyJzdWIiOiJhZG1pbkBhZG1pbiIsInJvbGVzIjpbeyJhdXRob3JpdHkiOiJST0xFX0FETUlOIn1dLCJpYXQiOjE1OTU0NTE3NDgsImV4cCI6MTU5NTQ1NTM0OH0.Q2oOQjXxm2sSY77STxP6iFglI6LZ-qmIQpaEdTK0D0o' 
```
- Request
 ```json
{
    "name" : "Test Product 1",
    "amount" : 133.5,
    "categoryId": 1,
    "quantity" : 22
}
```
- Response HTTP 200 OK

if there is invalid token or there isn't token, response http code would be HTTP 403 FORBIDDEN


## All enpoints

![](src/main/resources/static/api_endpoints.png)
