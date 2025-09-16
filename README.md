Android Application: Internship Application Simulator

This project is a mobile application developed in Android Studio that simulates the process of applying for an internship. It features a robust data entry form and a separate activity to display a summary of the application data. The application is designed with a focus on core Android development principles, including user interface design, data persistence with SQLite, and handling of a device's lifecycle.

Technical Stack

Languages: Kotlin, SQL

Development Environment: Android Studio

Data Persistence: SQLite database

Core Concepts: Activities, Layouts (e.g., LinearLayout, ConstraintLayout), UI Controls, Resource Files (strings.xml, colors.xml), Data Persistence, Activity Lifecycle Management

Core Features

Data Entry Form: The primary activity is a data entry form that allows users to input at least eight pieces of information using four or more different controls (e.g., EditText, Spinner, DatePicker, CheckBox). This demonstrates a strong understanding of user interface design and form validation.

Database Storage: A local SQLite database is used to store all the data submitted through the form. This showcases proficiency in data persistence and database management within an Android application.

Data Summary Display: The second activity queries the SQLite database and displays a summary of the data entered by the user. This demonstrates the ability to retrieve and present data from a local database.

Resource Management: All strings and colors are stored in their respective resource files (strings.xml and colors.xml), following best practices for localization and theme management.

Device Lifecycle Handling: The application correctly saves and reloads data upon device rotation, ensuring a seamless user experience and preventing data loss.

Project Details & Implementation

User Interface: I designed the layouts for both the data entry and display activities. Attributes within the views were set to control the positions and colors, ensuring a clean and intuitive design.

Backend Logic: The application uses Kotlin to handle the logic for the form submission, including inserting data into the SQLite database. The display activity contains the code to query the database and populate the summary view.

Data Persistence: I implemented the SQLite database from scratch to manage the storage and retrieval of application data. This was a key part of the project that solidified my understanding of local data management on Android.

Future Enhancements

Add a feature to edit existing applications.

Implement a search function to quickly find specific applications in the database.

Integrate a cloud-based database for cross-device data synchronization.
