# Calendar App

## Overview

Calendar application to organize meetings and schedule your plan. Application allow to synchronize plans with Google Calendar

## Technology used

- SpringBoot v2.1.2
- SpringSecurity - JWT
- AWS ElasticBeanStalk
- AWS DynamoDB (NOSQL database)
- AWS CodePipeline (AWS CodeCommit, AWS CodeBuild)
- React
- Google Calendar API

## Preview

![main-view](https://drive.google.com/uc?export=view&id=1G4gfgM2MDL_yrnQU5N06xPqw0TUpiVZG)

![login](https://drive.google.com/uc?export=view&id=1ad0Wm5dCQju_ebb1_KP1ea8d36VrIP9u)

## Running 

### `mvn clean -D package spring-boot:run`

Runs the app in the development mode.<br />
API is available under address [http://localhost:5000](http://localhost:5000)

## Response 

Application returns Events from calendar in JSON format. Time of events is returned in unix timestamp string.

## Authorization

All API requests require the use of a generated token. <br>
You can generate API key, by navigating to the /api/auth endpoint. 

The token need to be added for each request to each endpoint 
except /api/auth endpoint. <br>

Token need to be placed in header of the request as follow:
Authorization: "Bearer <TOKEN_VALUE>"


## Response Status Codes

Calendar App returns the following status codes in its API:

| Status Code | Description |
| :--- | :--- |
| 200 | `OK` |
| 201 | `CREATED` |
| 206 | `PARTIAL_CONTENT` |
| 400 | `BAD REQUEST` |
| 401 | `UNAUTHORIZED` |
| 404 | `NOT FOUND` |
| 500 | `INTERNAL SERVER ERROR` |
