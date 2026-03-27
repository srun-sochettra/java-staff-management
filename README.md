# Staff Member Management System

This repository contains the solution for Homework 02: StaffMember. 

## Objective
The primary objective of this project is to implement and demonstrate an understanding of OOP Concepts, Collections, Exceptions, Java 8-11, and StreamAPI.

## Features
* **Insert Employee**: Add new staff members to the database.
* **Display Employee**: View the list of current staff members.
* **Update Employee Information**: Modify existing records, with the exception of the employee ID.
* **Remove Employee**: Delete staff members from the system.
* **Dynamic Functionality**: Utilizes collections framework for dynamic updates, removals, displays, and pagination.

## Architecture and Class Structure
The application follows the Model-View-Controller (MVC) and Data Access Object (DAO) design patterns, structured across `StaffController`, `StaffView`, `StaffDAO`, and `DatabaseHelper`.

The domain models include:
* `StaffMember`: An abstract base class containing an abstract `pay()` method.
* `Volunteer`: Inherits from `StaffMember`.
* `SalariedEmployee`: Inherits from `StaffMember`.
* `HourlySalaryEmployee`: Inherits from `StaffMember`.

## Prerequisites
* Java Development Kit (JDK) 8 or higher
* PostgreSQL Server

## Database Configuration
The application connects to a PostgreSQL database. Ensure your local PostgreSQL instance is running and configured with the following default details found in `DatabaseHelper.java`:
* **URL**: `jdbc:postgresql://localhost:5432/staff_db`
* **Username**: `postgres`
* **Password**: `12345`

The application will automatically create the `staff_members` table and necessary sequences upon execution of `Main.java`.
