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

## Features
### For Library Staff:
- **User Management**: Create, update, and delete user profiles (username, password, name, email, role, membership type).
- **Membership Management**: Create and update user membership types (FREE, STANDARD, PREMIUM, PREMIUM_PLUS).
- **Book Management**: Add, update, and delete book records.
- **Borrowing Management**: Track borrowing records, including start and end dates, and overdue status.
### For Members:
- **View Available Books**: Check available books and their quantities.
- **Check Membership Status**: View current membership status and any outstanding dues for overdue books.
- **Borrowing History**: View a list of borrowed books with relevant details.

## Project Setup

### Follow these steps to set up the project:

1. **Clone the Repository**:
   ```bash
   git clone <repository-url>
   cd <project-directory>
   
2. **Check Java and Maven Versions**: 
   - Ensure that you have Java 20 and Maven 3.8.6 installed. You can check the versions using:
   ```bash
   java -version
   mvn -version

3. **Configure Database**:
   - Create a `.env` file in the project root and define the following variables:
   ```bash
     DB_NAME=library_management_db
     DB_USERNAME=<your_db_username>
     DB_PASSWORD=<your_db_password>
4. **Update the `application.properties` file with your MySQL username and password**:
   ```bash
   spring.datasource.url=jdbc:mysql://localhost:3306/${DB_NAME}
   spring.datasource.username=${DB_USERNAME}
   spring.datasource.password=${DB_PASSWORD}

5. **Create Database**:
   - Open MySQL Workbench or use the command line to create the database:
   ```bash
     CREATE DATABASE library_management_db;

7. **Run the Application**:
   - Start the Spring Boot application. This will create the necessary entities in the database.

8. **Populate Initial Data**: 
   - Use the provided SQL insert script to populate the database with initial data. This can be done through MySQL Workbench or the command line:
   ```bash
   -- Insert of users in table users
   INSERT INTO users (username, password, first_name, last_name, email, role, membership_type) VALUES
   ('memberUser', '$2a$10$S77Ps5ZvQpS/QqtldgRAEOdblf75d9zM3XuHm7cBO2XdG01K8gDVG', 'Member', 'User', 'member@example.com', 'MEMBER', 'PREMIUM'),
   ('adminUser', '$2a$10$vI5NNoxXQmmtowG2eDsiUuVPI6LKvwknNwQ3afAHGrDBQv8LWsbcC', 'Admin', 'User', 'admin@example.com', 'EMPLOYEE', 'PREMIUM_PLUS'),
   ('memberUser2', '$2a$10$S77Ps5ZvQpS/QqtldgRAEOdblf75d9zM3XuHm7cBO2XdG01K8gDVG', 'Member2', 'User2', 'member2@example.com', 'MEMBER', 'FREE');
   
   -- Insert of books in table books
   INSERT INTO books (title, book_genre, star_rating, stock, quantity) VALUES
   ('Pride and Prejudice', 'CLASSICS', 4.5, 'In Stock', 10),
   ('Dune', 'SCIENCE_FICTION', 4.7, 'In Stock', 5),
   ('The Girl with the Dragon Tattoo', 'THRILLER', 4.3, 'In Stock', 7),
   ('Meditations', 'PHILOSOPHY', 4.8, 'Out of Stock', 0),
   ('The Da Vinci Code', 'MYSTERY', 4.4, 'In Stock', 6),
   ('The Notebook', 'ROMANCE', 4.6, 'In Stock', 8),
   ('Gone Girl', 'CRIME', 4.5, 'In Stock', 4),
   ('The Book Thief', 'HISTORICAL_FICTION', '4.7', 'Out of Stock', 0);
   
   -- Insert of borrowings in table borrowings
   INSERT INTO borrowings (user_id, book_id, borrow_start_date, borrow_end_date, comments, debt_amount) VALUES
   (1, 1, '2024-09-01', '2024-09-15', 'No comment', 100.0),
   (2, 2, '2024-09-10', '2024-09-24', 'Need to return before 24.09.', 0.0),
   (1, 3, '2024-09-12', '2024-09-26', 'I like this book a lot', 0.0),
   (3, 4, '2024-09-05', '2024-09-20', 'Interesting philosophy', 100.0),
   (2, 5, '2024-09-15', '2024-09-29', 'Mistery with big plot', 0.0),
   (3, 6, '2024-09-20', '2024-10-05', 'Love story', 0.0),
   (1, 7, '2024-09-10', '2024-09-24', 'Really intense criminal story', 0.0),
   (2, 8, '2024-09-01', '2024-09-15', 'Recommend for everyone!', 100.0);

9. **Restart the Application**:
   - Restart the Spring Boot application to ensure the database is fully set up with the initial data.
  
## Test the Endpoints

Use Postman to test the application. You will find a Postman collection file included in this repository for convenience.

1. **Import the Postman Collection**: Import the provided Postman collection into your Postman workspace.

2. **Login with Test Users**: Use the **Login** endpoint included in the Postman collection to authenticate. You can log in with one of the test users provided in the SQL script from step 7. 
   - For the admin user, the password is **admin**.
   - For both other test users, the password is **user**.

3. **Generate Bearer Token**: After successfully logging in, a bearer token will be generated. Copy this token.

4. **Use the Token**: Set the authorization type to **Bearer Token** in Postman and paste the copied token into the Authorization header for all subsequent requests. 

*Important: Most endpoints are restricted and can only be accessed by admin/employee users (as excpected behavoir). To test these endpoints, log in as the admin user (see section 7, where the admin user is defined).*
<div align="center">
   
 ![Postman Collection Import Example](https://github.com/user-attachments/assets/d2bad825-10d6-474a-8ea9-a2f194b9b68f)

 *This is how the Postman collection should look when imported.*

 ## For any additional questions or clarifications, feel free to reach out to me.
</div>


