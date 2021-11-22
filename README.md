# site-clearing-simulation

## Overview
The following repository contains project for site clearing simulation.
* JDK 1.8 is used for development
* Gradle 6.8 is used as build tool
* Checkstyle 8.36.1 is used for coding standards
* Jacoco 0.8.5 is used for test coverage analysis

### Requirements
* JDK 1.8

### Build
This project uses gradle to build and test the application.

Use the below command on terminal to build the project
	`./gradlew clean build`

The above command will
* Run unit tests and generate test report:
	`./build/reports/tests/testindex.html`
* Generate checkstyle report:
	`./build/reports/checkstyle/main.html`
* Generate Jacoco coverage report:
	`./build/jacoco/test/html/index.html`
* Generate executable jar file        
	`./build/libs/site-clearing-simulation.jar`
        
### Run
The application requires a file containing the site matrix to run.
The complete file path + file name should be provided as argument to executable jar.
Use the below command to run the application: `java -jar site-clearing-simulation <filepath+filename>`