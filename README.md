# Wine Factory Project

A desktop Java application built with JavaFX, Maven, and Hibernate.

## Features
- JavaFX user interface
- Maven project structure
- Hibernate configuration
- SQL Server database connection

## Technologies
- Java
- JavaFX
- Maven
- Hibernate
- SQL Server

## Project Structure
- `src/` - source code and resources
- `pom.xml` - Maven configuration
- `hibernate.cfg.xml` - Hibernate database configuration

## How to Run
1. Clone the repository.
2. Open the project in IntelliJ IDEA or another Java IDE.
3. Make sure Maven dependencies are installed.
4. Configure the database connection in `hibernate.cfg.xml`.
5. Run the main JavaFX application class.

## Notes
- The project uses Hibernate for database access. [file:1527]
- The Hibernate configuration currently references a local SQL Server connection on `127.0.0.1:1433`. [file:1527]
- Update the username and password in `hibernate.cfg.xml` if needed for your local setup. [file:1527]
