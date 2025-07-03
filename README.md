 JavaFX Student Management System
This is a JavaFX desktop application for managing student records. It supports login functionality and full CRUD (Create, Read, Update, Delete) operations using a MySQL database. The project is part of a programming test and is implemented with proper MVC structure and manual ORM techniques.
Developer Information

Test Instructions Mapping
 Section A: Scene Creation 

- Developed two separate scenes using JavaFX:
  - Login Scene – accepts username and password from the user.
  - CRUD Scene – displays a `TableView` and forms to add, update, or delete student records.
- Layouts used: `VBox`, `HBox`, `GridPane`, and `BorderPane`.
- Navigation between scenes handled through event actions.

Section B: Database Design 

- Database Name:`student`
- Tables:
  - `users` – stores login credentials (`id`, `username`, `password`)
  - `students` – stores student records (`id`, `name`, `email`, `age`)
- MySQL screenshots of:
  - Designer View of tables
  - Sample data in table
- Stored in `screenshots/database/` folder.

Section C: Controller and View Implementation (160 Marks)

- Controllers handle user interaction logic:
  - Login validation
  - Fetching, inserting, updating, deleting student records
- Views are built using JavaFX components and maintain a user-friendly structure.
- Bindings and event handlers are set for smooth data flow between model and UI.

Section D: Perform Login & CRUD Operation (400 Marks)

- Login credentials are validated from the `users` table.
- Full CRUD operations implemented:
  - Create – Add new student via form inputs
  - Read – Display student data in a `TableView`
  - Update – Modify selected student's details
  - Delete – Remove selected student
- Database connection handled using **JDBC**.

Section E: Data Modelling (120 Marks)

- Created a `Student.java` class to represent the `students` table.
- Class includes:
  - Fields: `id`, `name`, `email`, `age`
  - Constructors, getters, and setters
- Used as an ORM-like model to map data between the application and database.

Requirements

- Java JDK 17+
- JavaFX SDK (21+)
- MySQL Server
- VS Code or IntelliJ
  Note: Since the code is a bit difficult for me. I researched a lot from websites like W3schools and Chatgpt to make the best version of my code. For some of the portion I took help from my classmate Nimesh and Suman.
Name:Niraj Bhandari  
Student ID: 23093760  
Email: bhandariniraj081@gmail.com  
Date of Submission: 3rd July 2025  

