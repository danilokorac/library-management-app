# Library Management Application

## Project Overview

This project is a Library Management Application designed to help library staff manage member activities, book inventories, and allow members to view available books, their membership status, and dues. 
The application encompasses user management, book management, and borrowing records management.

## Requirements

To run this project, you will need the following:

- **Java 20**
- **Maven 3.8.6**
- **MySQL 8** (using MySQL Workbench)
- **Postman** for testing endpoints
- **IntelliJ IDEA** (optional, any IDE can be used)

## Project Setup

Follow these steps to set up the project:

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <project-directory>
   
2. **Check Java and Maven Versions: Ensure that you have Java 20 and Maven 3.8.6 installed. You can check the versions using**:
  java -version
   mvn -version

3. **Configure Database**:
   Create a .env file in the project root and define the following variables:
     DB_NAME=library_management_db
     DB_USERNAME=<your_db_username>
     DB_PASSWORD=<your_db_password>
4. **Create Database**:
   Open MySQL Workbench or use the command line to create the database:
     CREATE DATABASE library_management_db;
5. **Run the Application**:
   Start the Spring Boot application. This will create the necessary entities in the database.
6. **Populate Initial Data:
   Use the provided SQL insert script (included in the project) to populate the database with initial data. This can be done through MySQL Workbench or the command line:
7. **Restart the Application**:
   Restart the Spring Boot application to ensure the database is fully set up with the initial data.
  
## Test Endpoints
To test the application, you can use Postman. The following steps will guide you:

1. Login:
   Use one of the test users provided in the SQL insert script to log in.
2. Test Endpoints:
   Import the included Postman collection (JSON file) to test various endpoints.
   Perform the desired operations and verify responses.
