# Spring Boot Multi-Module Project 
This is a README file for a Spring Boot Multi-Module project consisting of four modules: controller, model, repository, and entity. 
Each module plays a specific role in implementing the functionality of the application.

# Prerequisites
1. Java development Kit(JDK) installed on your machine.
2. Integrated Development Environment (IDE).
3. MYSQL installed and Running.

# Tools and Technologies
* Tools : Intellij.
* Technologies : Java, Springboot, MYSQL.
* Apache Maven.
* API Testing Tool : Postman.



# Project Structure

    project-root

    ├── Model 

    ├── Repository
 
    ├── Service

    ├── Controller
 
    └── pom.xml


Create a new Spring Boot project:

Use the Spring Initializr website (https://start.spring.io) to generate a new Spring Boot project with the required dependencies, including Spring Web, Spring Data JPA, and MySQL driver.

The project structure follows a modular approach, separating concerns into different modules. Here is an overview of the modules:
1. ****Model Module**:**
The model module includes the data transfer objects (DTOs) or request/response models used for communication between the client and the server. It defines the structures of the data passed in requests and responses.

2. **Repository Module:**
   The repository module deals with data access and storage. It includes interfaces or classes that provide methods for interacting with the underlying database or data source.
   
3. **Service Module:**
   The service module contains the business logic of the application. It includes classes that implement the business rules and workflows, data operations, and interact with repositories.
  
4. **Controller Module:**
This module contains the controllers responsible for handling incoming HTTP requests and defining the API endpoints. Controllers receive requests, process them, interact with the service layer, and return appropriate responses. 

# Setup
### Clone the Repository
1. Open your terminal or command prompt.

2. Change to the directory where you want to clone the project.

3. Clone the repository using the following command:
   **$ git clone [repository-url]**

# Getting Started
Ensure you have Java and Maven installed on your machine.

Open a terminal or command prompt and navigate to the project root directory.

Build the project by running the following command:

    $ mvn clean install

Once the build is successful, execute the appropriate command:

    $ mvn spring-boot:run

To Get API Response open your Web Browser and enter the API, for example

       http://localhost:8080/getall


 **Command Line to access EC2 instance:**

    ssh -i springapp.pem ubuntu@ec2-13-48-248-212.eu-north-1.compute.amazonaws.com
    

## This Application has hosted on Amazon Web Services (AWS)
###  EC2 instance and Relational Database Service(RDS)

To view the data available in EC2 & RDS, accessible via the URL provided below:

    http://ec2-13-48-248-212.eu-north-1.compute.amazonaws.com:8080/getall

Use Terminal or Api testing tool like postman:

    To Get: curl http://ec2-13-48-248-212.eu-north-1.compute.amazonaws.com:8080/getall
    To Post: curl -X POST -H "Content-Type: Application/json" -d '{"key1": "value1", "key2": "value2"}' http://ec2-13-48-248-212.eu-north-1.compute.amazonaws.com:8080/insert
    EG: curl -X POST -H "Content-Type: Application/json" -d '{"address": "london","age": "22","name":"kaviya"}' http://ec2-13-48-248-212.eu-north-1.compute.amazonaws.com:8080/getall
  
# credentials
* **Ec2 Instance name:** springapp
* **Public IPv4 DNS:** ec2-13-48-248-212.eu-north-1.compute.amazonaws.com
* **Public IPv4 address:** 13.48.248.212
* **Port:** 8080
* **password:** Kaviya@99


* **RDS Instance name:** springapp
* **RDS Database name:** springapp
* **User Name:** kaviya
* **Password:** Kaviya99
* **End point:** springapp.cyi0cfbhrwqo.eu-north-1.rds.amazonaws.com
* **port:** 3306

# Docker
*   **Username:** kaviya1499
*   **password:** Kavikasi@99
*   **Repossitory name:** springboot_docker

## Two properties file links
To run local database 

     mvn clean install -Plocal
     mvn spring-boot:run -Plocal

To run RDS database

    mvn clean install -Pprod
    mvn spring-boot:run -Pprod

# Login Credentials for the application
*   **Username:** admin
*   **password:** admin@123


# Usage
Once the application is deployed, you can interact with it using HTTP requests.
1. Create: POST /entity - Create a new entity by sending a JSON payload with the required data.
2. Read: GET /entity - Retrieve an entity by its unique identifier.



