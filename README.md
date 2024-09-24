# Library Management Application

## Project Overview

This project is a Library Management Application designed to help library staff manage member activities, book inventories, and allow members to view available books, their membership status, and dues. The application encompasses user management, book management, and borrowing records management.

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
   ```bash
     DB_NAME=library_management_db
     DB_USERNAME=<your_db_username>
     DB_PASSWORD=<your_db_password>

5. **Create Database**:
   Open MySQL Workbench or use the command line to create the database:
   ```bash
     CREATE DATABASE library_management_db;

7. **Run the Application**:
   Start the Spring Boot application. This will create the necessary entities in the database.

8. **Populate Initial Data:
   Use the provided SQL insert script to populate the database with initial data. This can be done through MySQL Workbench or the command line:
   ```bash
   -- Insert of users in table users
   INSERT INTO users (username, password, first_name, last_name, email, role, membership_type) VALUES
   ('memberUser', '$2a$10$S77Ps5ZvQpS/QqtldgRAEOdblf75d9zM3XuHm7cBO2XdG01K8gDVG', 'Member', 'User', 'member@example.com', 'MEMBER', 'PREMIUM'),
   ('adminUser', '$2a$10$vI5NNoxXQmmtowG2eDsiUuVPI6LKvwknNwQ3afAHGrDBQv8LWsbcC', 'Admin', 'User', 'admin@example.com', 'EMPLOYEE', 'PREMIUM_PLUS'),
   ('memberUser2', '$2a$10$S77Ps5ZvQpS/QqtldgRAEOdblf75d9zM3XuHm7cBO2XdG01K8gDVG', 'Member2', 'User2', 'member2@example.com', 'MEMBER', 'FREE');
   
   -- Insert of books in table books
   INSERT INTO books (title, book_genre, star_rating, stock, quantity) VALUES
   ('Pride and Prejudice', 'CLASSICS', '4.5', 'In Stock', 10),
   ('Dune', 'SCIENCE_FICTION', '4.7', 'In Stock', 5),
   ('The Girl with the Dragon Tattoo', 'THRILLER', '4.3', 'In Stock', 7),
   ('Meditations', 'PHILOSOPHY', '4.8', 'Out of Stock', 0),
   ('The Da Vinci Code', 'MYSTERY', '4.4', 'In Stock', 6),
   ('The Notebook', 'ROMANCE', '4.6', 'In Stock', 8),
   ('Gone Girl', 'CRIME', '4.5', 'In Stock', 4),
   ('The Book Thief', 'HISTORICAL_FICTION', '4.7', 'Out of Stock', 0);
   
   -- Insert of borrowings in table borrowings
   INSERT INTO borrowings (user_id, book_id, borrow_end_date, borrow_start_date, comments) VALUES
   (1, 1, '2024-09-01', '2024-09-15', 'No comment'),
   (2, 2, '2024-09-10', '2024-09-24', 'Need to return before 24.09.'),
   (1, 3, '2024-09-12', '2024-09-26', 'I like this book a lot'),
   (3, 4, '2024-09-05', '2024-09-20', 'Interesting philosophy'),
   (2, 5, '2024-09-15', '2024-09-29', 'Mistery with big plot'),
   (3, 6, '2024-09-20', '2024-10-05', 'Love story'),
   (1, 7, '2024-09-10', '2024-09-24', 'Really intense criminal story'),
   (2, 8, '2024-09-01', '2024-09-15', 'Recommend for everyone!');

9. **Restart the Application**:
   Restart the Spring Boot application to ensure the database is fully set up with the initial data.
  
## Test Endpoints
To test the application, you can use Postman. The following steps will guide you:

1. **Login**:
   Use one of the test users provided in the SQL insert script to log in.
2. **Test Endpoints**:
   Import the included Postman collection (JSON file) to test various endpoints.
   Perform the desired operations and verify responses.
