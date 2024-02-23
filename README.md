# Spring Boot Messaging Application

## Pipelinecodare 

This Spring Boot application is designed to manage messages through RESTful APIs, with CRUD operations for adding, retrieving, updating, and deleting messages. It integrates with MySQL for data storage. The project is configured with a CI/CD pipeline using GitHub Actions and AWS CodeBuild to automate the build and deployment process.

## Prerequisites

- JDK 17
- Maven
- MySQL
- AWS Account for AWS CodeBuild and Elastic Beanstalk

## Local Setup

1. Clone the repository:
git clone https://github.com/Alexanua/pipelinecodare.git

2. Navigate to the project directory:
   cd pipelinecodare
3. Update `src/main/resources/application.properties` with your MySQL configuration.
4. Start the application:
   mvn spring-boot:run


## API Endpoints

- **POST** `/api/messages/addMessage` - Add a new message.
- **GET** `/api/messages/getMessages` - Get all messages.
- **GET** `/api/messages/{id}` - Get a message by ID.
- **PATCH** `/api/messages/{id}` - Update a message by ID.
- **DELETE** `/api/messages/{id}` - Delete a message by ID.

## CI/CD Pipeline

### GitHub Actions

The `.github/workflows/main.yml` file defines the CI/CD pipeline for GitHub Actions:

```yaml
name: Spring Boot CI/CD Pipeline

on:
push:
 branches: [ main ]
pull_request:
 branches: [ main ]

jobs:
build-and-test:
 runs-on: ubuntu-latest

 steps:
   - uses: actions/checkout@v2
   - name: Set up JDK 17
     uses: actions/setup-java@v2
     with:
       java-version: '17'
       distribution: 'temurin'
   - name: Build and Test with Maven
     run: mvn clean install

AWS CodeBuild buildspec.yml
The buildspec.yml file for AWS CodeBuild is as follows:
version: 0.2

phases:
  build:
    commands:
      - echo "packaging jar file .."
      - mvn clean package

post_build:
  commands:
    - echo "build complete"

artifacts:
  files:
    - target/pipelinecodare.jar
  discard-paths: yes

 ## Deployment
Deployment
The application is deployed using AWS Elastic Beanstalk. The live application can be accessed via the provided URL after deployment.

Contributing
We welcome contributions. Please fork the repository, make your changes, and submit a pull request.

License
This project is licensed under the MIT License. See the LICENSE file for more information.
