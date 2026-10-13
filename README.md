Course Registration Service

Project Overview



This project is a Java/Maven Course Registration service. It uses JUnit 5 for automated testing and a Jenkins Declarative Pipeline for continuous integration.



Project Structure

Student-Starter/

├── pom.xml

├── Jenkinsfile

├── README.md

└── course-registration-app/

&#x20;   ├── pom.xml

&#x20;   └── src/

&#x20;       ├── main/java/com/university/

&#x20;       │   ├── Course.java

&#x20;       │   ├── Student.java

&#x20;       │   └── RegistrationService.java

&#x20;       └── test/java/com/university/

&#x20;           ├── CourseTest.java

&#x20;           └── RegistrationServiceTest.java



Maven Parent-Child Structure



The root pom.xml is the parent POM. It uses pom packaging, declares the course-registration-app module, defines shared Java and JUnit versions, and manages dependency and plugin versions.



The child module inherits from the parent through ../pom.xml. It contains the application source code and JUnit 5 tests and produces the application JAR.



Prerequisites

Java Development Kit 17

Apache Maven

Git

Build and Test



Run the following commands from the repository root.



Run a clean build and test:



mvn clean test





Package the application:



mvn package





Run the complete verification lifecycle:



mvn clean verify





The child-module JAR is generated at:



course-registration-app/target/course-registration-app-1.0-SNAPSHOT.jar



JUnit XML reports are generated under:



course-registration-app/target/surefire-reports/



Jenkins Pipeline



The Jenkinsfile defines a Windows-agent Jenkins Declarative Pipeline with Checkout, Build, Test, and Package stages.



The pipeline uses bat commands, publishes JUnit test reports, and archives the generated JAR. Jenkins must have the configured JDK17 and Maven3 tools available, and the job must be connected to the repository.



A live Jenkins execution is not claimed unless the pipeline has actually been run on a Jenkins instance.

