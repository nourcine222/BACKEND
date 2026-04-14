# Training Backend

Backend Spring Boot for the "gestion de formation" mini project.

## Scope from the brief

The project manages:
- Utilisateurs and roles
- Domaines
- Profils
- Structures
- Participants
- Formateurs
- Employeurs
- Formations

## Current backend foundation

- Spring Boot 3.5.13
- Java 21 target
- Spring Web
- Spring Data JPA
- Bean Validation
- Lombok
- PostgreSQL driver
- DevTools

## Domain model

- `AppUser` belongs to one `Role`
- `Participant` belongs to one `Structure` and one `Profil`
- `Formateur` can be `INTERNE` or `EXTERNE`
- `Formateur` may belong to an `Employeur`
- `Formation` belongs to one `Domaine`
- `Formation` may have one `Formateur`
- `Formation` and `Participant` are many-to-many

## Backend analysis notes

The brief is mostly a data-management application, so the backend should be built around CRUD APIs and validation rules first.

Recommended next steps:
- add service layer for each aggregate
- add REST controllers for CRUD
- add authentication and role-based access control
- add DTOs to avoid exposing entities directly
- add Flyway migrations once the schema is stable
- add tests for validation and repository logic

## Local setup

1. Make sure PostgreSQL is running on `localhost:5432`
2. Create a database named `training_backend`
3. Check `src/main/resources/application.properties`
4. Run:

```powershell
.\mvnw.cmd test
.\mvnw.cmd spring-boot:run
```

## PostgreSQL config

The backend already uses PostgreSQL by default:

- URL: `jdbc:postgresql://localhost:5432/training_backend`
- Username: `postgres`
- Password: `postgres`

If your local PostgreSQL user or password is different, update:

```properties
spring.datasource.username=your_user
spring.datasource.password=your_password
```

For a fresh local setup, you can create the database with:

```sql
CREATE DATABASE training_backend;
```
