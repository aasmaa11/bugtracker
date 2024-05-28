# Bugtracker Project

Web app that allows developers, project managers and administrators to track issues of a software project.

## Tech
* Spring Boot backend
* Authentification with Spring Security
* Authorization with JWT
* Vue frontend
* Axios used to perform API requests

## Functionalities
* Signup, signin, signout
* Create, edit and delete software projects (can be done only by admins and project managers)
* Create, edit and delete tickets
* View list of users and modify users (can be done only by admins)
* View list of projects and tickets

## To run the backend:
```
cd SpringBootRestApp
gradlew bootRun
```

## To run the frontend:
```
cd bugtracker-frontend
npm run serve
```

## To run backend tests:
```
cd SpringBootRestApp
gradlew test
```
