# Student Management System

A **Java-based Student Management System** integrated with **Oracle Database** using JDBC. It allows users to manage student records and calculate academic performance.

## Features

* Add student details
* View all students
* Update student information
* Delete student records
* Calculate GPA from subject marks
* Calculate average GPA by branch
* Store student and GPA data in Oracle Database
* Prevent duplicate roll numbers

## Technologies Used

* Java
* Oracle Database
* JDBC

## GPA Calculation

The GPA is calculated by finding the average of the marks entered for all subjects:

**GPA = Total Marks / Number of Subjects**

The calculated GPA is then stored in the Oracle database and can be used to find the average GPA of students in a particular branch.

## Database

The project uses **Oracle Database (XE)** with JDBC for database connectivity.

## How to Run

1. Set up Oracle Database and create the `student1` table.
2. Configure the Oracle username, password, and connection details.
3. Add the Oracle JDBC driver to the project.
4. Compile and run `StudentManagement.java`.
5. Use the menu to manage students and calculate GPA.
