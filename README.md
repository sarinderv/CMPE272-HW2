# CMPE272-HW2
Team "Duke" HW#2

HW2#Twitter Service:

curl -X GET http://132.145.52.5:8080/tweet?id=1438978872972910592

curl -X POST http://132.145.52.5:8080/tweet?text=hello%20from%20twitter%20web%20service%20cloud

curl -X DELETE http://132.145.52.5:8080/tweet?id=1438971036683362318

curl -X GET http://132.145.52.5:8080/timeline

rest-service-HW2.zip ## Java Spring Maven project 

## Spring Boot

Backend is a Spring Boot REST service. Please see https://spring.io/guides/gs/spring-boot/ for more info.

To run the service on localhost:8080, install Maven and then enter command `mvn spring-boot:run`

App and Controller are in src/main/java

Unit Tests are in src/test/java

To run unit tests enter command `mvn test`

## React

Frontend is a React app using Axios to make the HTTP calls.

To run the app in dev mode on localhost:3000, enter command `npm start`

touch1
