# Frontend Service

Requirements 
For building and running the application you need:
# Technologies
1) JDK 17
2) Apache Maven 3


**Build and Deploy the Application**

Navigate to the project, and build App

1) Clone the application New
2) Navigate to the project, and build App
3) mvn clean install (Build and run the app using maven)
4) Navigate to the application (battleship-game-engine)
5) run the app (mvn spring-boot:run) or java -jar jarName.jar
6) The app will start running at http://localhost:8081
7) Swagger url http://localhost:8081/swagger-ui.html
11) H2 Database Url : http://localhost:7001/h2-ui/
12) JDBC URL :jdbc:h2:mem:testdb
13) user name:sa and password:sa

**Libraries used**
 1) Spring Boot 2.6.3.RELEASE
 2) Spring Boot Test
 3) JSON Library(javax.json)
 4) Swagger API
 5) Hibernate Validation API (org.hibernate)
 6) H2 Database


**Features**

  **About the Service**
  This is Service just a simple REST service to get the data persistence layer and return to ui. It uses an in-memory database (H2) to store and retrieve the data

These services can perform,
1) Register The User
2) Get the Token
3) GET and Update Games

Note :
     

API Insert Data Register User :

1) Register user
   http://localhost:8081/login-service/user-details/
   Method Post
  Body :
{
   "username": "user",
   "password": "password"
   }


2) Get the Auth Token
   Method Post
   Body :
   {
   "username": "user",
   "password": "password"
   }