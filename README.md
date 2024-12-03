Demo Task - README
Introduction
Welcome to the Demo Task app! This application is designed using the MVVM (Model-View-ViewModel) architectural pattern. This README provides an overview of our app architecture and the reasons for choosing MVVM.

App Architecture
MVVM Pattern
The MVVM architecture divides the application into three main components:

Model

Purpose: Handles data and business logic. It includes data sources like APIs and databases.
View

Purpose: Manages the UI and user interactions. It displays data and captures user input.
ViewModel

Purpose: Acts as a bridge between the Model and View. It holds and manages UI-related data, and ensures the View is updated when the data changes.
Why MVVM?
Separation of Concerns: MVVM clearly separates the UI, business logic, and data, making the code more modular and maintainable.

Testability: It isolates business logic from the UI, making unit testing easier and more effective.

Lifecycle Awareness: ViewModel is lifecycle-aware, preserving data across configuration changes like screen rotations.

Data Binding: Simplifies synchronizing the UI with data changes, reducing boilerplate code.

Scalability: Facilitates the addition of new features with minimal impact on existing code.