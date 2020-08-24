# Quora - group project for Upgrad Dec'19 Blockchain batch
#Dev Changes done by:
#REST API endpoints - 1 - Mithun Suresh
#REST API endpoints - 2 - Aravind Makam
#REST API endpoints - 3 - Mehuli Mukherjee

#Integrated and fixes done by Mehuli Mukherjee

#The project is made using Swagger user interface and the data is stored in the PostgreSQL database. Also, the project has been implemented using Java Persistence API (JPA).

#Build the project in the main directory of the project using "mvn clean install -DskipTests". In order to activate the profile setup, move to quora-db folder using "cd quora-db" command in the terminal and then run "mvn clean install -Psetup" command to activate the profile setup.

Since the database is not mocked, "quora_test.sql" file is given in the stub to create the records in the database to pass all the test cases. All the test cases would only pass if you have these records in the database. Therefore, before running each test case you need to ensure that the database contains all the records given in "quora_test.sql" file. As otherwise, the test case may not pass even if the code implementation is correct.

#Project Structure

#The project followed a definite structure in order to help the co-developers and reviewers for easy understanding.The main module is divided into three sub-modules —  quora-api, quora-db, and quora-service.

1. quora-api

config - This directory consist of all the required configuration files of the project.
controller - This directory consist of all the controller classes required for the project (the list of required controllers along with the API endpoints are listed in the next segment).

exception - This directory consist of the exception handlers for all the exceptions.

endpoints - This directory consists of the JSON files which are used to generate the Request and Response models.

test - This directory consists of tests for all the controller classes. Have uncommented all the given test cases to run these test cases after implementing the project.

 

2. quora-db

config - This directory consists of the database properties and environment properties for local development.
sql - This directory consists of all the SQL queries to create database schema tables.
 

3. quora-service

business - This directory consist of all the implementations of the business logic of the application.
dao - This directory allows us to isolate the application/business layer from the persistence layer and consist of the implementation of all the data access object classes.
entity - This directory consist of all the entity classes related to the project to map these class objects with the database.
exception - This directory consists of all the exceptions related to the project. All the exceptions required for the project have been implemented in the stub file.
 

Authentication and Authorization
The authentication functionality has been implemented in such a way that the API endpoints are accessible only when a user successfully logs in to the endpoint '/user/signin'. In such a case, the user should be able to subsequently access the endpoints by providing only the access token to each endpoint. Also, the user will have access to the endpoints defined in the 'AdminController' class based on the role information provided in the database, as defined below:

If the role of a user is 'admin', the user will be able to access all the API endpoints in the web application.

If the role of a user is 'nonadmin', the user cannot access the API endpoints defined in the AdminController class; he/she can, however, access the rest of the API endpoints defined in the other controller classes.
 

HTTP status
As you have already learnt about different HTTP response status codes, implement the same when creating the API endpoints and return the corresponding HTTP status code based on the functionality or message. The most commonly used response codes in this project are as follows:

HttpStatus.OK

HttpStatus.CREATED

HttpStatus.UNAUTHORIZED

HttpStatus.FORBIDDEN

HttpStatus.NOT_FOUND
