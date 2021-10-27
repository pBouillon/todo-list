# Todo-list

Simple and unambitious TODO list API

## Goal

The main goal of this project is to have a quick glance at the Java Spring
ecosystem regarding REST APIs and applying the interesting patterns and
principles (such as CQRS, hexagonal architecture, etc.)

## Tech stack

### Core

- Made in **Java 11**
- Made with **Spring Boot**

### Librairies

- API documentation with **SpringFox**
- Java utilities with **Lombok**
- Logging with **Log4j2**
- Testing with **AssertJ**, **Junit5** and **Mockito**
- Metrics with **Micrometer** and **Prometheus**

### Infrastructure

- Persistence with **MongoDB** (along with **mongo-express**)
- Containerization with **Docker**
- Visualization with **Grafana**

## Architecture overview

### API

- [**domain/**](api/src/main/java/io/pbouillon/todolist/domain)  
Contains the core domain objects such as the enums, entities and value objects

- [**infrastructure/**](api/src/main/java/io/pbouillon/todolist/infrastructure):  
Contains the configuration of any third party system used within the app

- [**application/**](api/src/main/java/io/pbouillon/todolist/application):  
Contains the business logic of the application. This layer act as an
intermediary between the presentation and the domain layer

- [**presentation/**](api/src/main/java/io/pbouillon/todolist/presentation):  
This layer exposes the data to the users and allows him to invoke its services
