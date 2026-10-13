Course Registration — DevOps Assessment
1. Project Overview

This project is based on the supplied Student-Starter repository. It contains a Java course-registration application, JUnit 5 tests, a multi-module Maven build, a Jenkins Declarative Pipeline, and Kubernetes manifests for deploying Nginx on Minikube.

2. Prerequisites
JDK 17
Maven 3
Git
Jenkins with JDK 17 and Maven configured, if running the pipeline
Docker, Minikube, and kubectl for Kubernetes tasks
3. Build and Execution Instructions

Run Maven commands from the repository root.

mvn clean compile
mvn clean test
mvn package


The application JAR is generated at:

course-registration-app/target/course-registration-app-1.0-SNAPSHOT.jar

The application currently has no standalone main class documented for launching a service. The JAR is a packaged build artifact.

Kubernetes deployment

Start Minikube and verify the cluster:

minikube start
kubectl get nodes


Apply the manifests:

kubectl apply -f deployment.yaml
kubectl apply -f service.yaml
kubectl rollout status deployment/nginx-deployment


Check the Deployment, Service, and Pods:

kubectl get deployments
kubectl get services
kubectl get endpoints nginx-service
kubectl get pods -l app=nginx -o wide


Access Nginx:

minikube service nginx-service --url


Keep the terminal open if required by the Minikube Docker driver on Windows.

4. Git Workflow and History

The feature branch used was feature/course-code-fix. The completed work was integrated into main.

Relevant commits from the recorded history include:

71d080b — Fix course code comparison and capacity validation
1e3d890 — Add regression tests for course code and capacity
8668b04 — Add Jenkins Declarative CI pipeline
81c924d — Refactor project into multi-module Maven build
bd929ce — Merge course registration assessment work
4f713f6 — Correct credit assertion in capacity test

The initial baseline commit was cef2bff.

Verify the final history and working tree:

git branch
git log --oneline -10
git status

5. Maven Parent-Child Structure

The root pom.xml is the parent POM, with packaging type pom. It defines shared properties, including the Java version, and manages the JUnit 5 dependency version.

The course-registration-app module contains the application source code and tests. Its child POM inherits from the root POM using the parent coordinates and relative path. The child declares JUnit 5 without repeating the managed version.

The root Maven reactor builds the parent and application module together.

Build and test evidence

The recorded Maven output showed:

Parent module: SUCCESS
Application module: SUCCESS
Tests run: 12
Failures: 0
Errors: 0
Skipped: 0
Packaging: BUILD SUCCESS

These results correspond to the recorded run on 13 October 2026. Rerun the commands if the source or tests change after that run.

6. Defect Fix and Assigned Change Request
Capacity boundary defect

The original capacity check incorrectly permitted registration when enrollment equaled capacity. The corrected condition rejects registration when:

currentEnrollment >= capacity

JUnit tests cover a full course and a course with exactly one seat remaining. They assert the registration result, student credit state, and enrollment count.

Question Set D — Case-insensitive duplicate registration

Course-code duplicate detection must be case-insensitive. Registering for CS101 must prevent subsequent registrations using cs101 or Cs101.

Normalization is applied consistently to registration storage and duplicate lookup. Tests verify that both duplicate attempts are rejected and do not change the student's credits or enrollment counts. Normal registration for a distinct course code must continue to work.

7. Jenkins Declarative Pipeline

The root Jenkinsfile targets a Windows Jenkins agent and uses bat steps. It assumes Jenkins tools named JDK17 and Maven3 are configured.

The stages are:

Checkout — checks out the configured source.
Build — runs mvn clean compile.
Test — runs mvn test.
Package — runs mvn package and archives course-registration-app/target/*.jar.

The Test stage publishes reports from course-registration-app/target/surefire-reports/TEST-*.xml.

Jenkins executes stages sequentially. If the Test stage fails, the pipeline skips the subsequent Package stage by default. Therefore, the package and archive steps are not executed after a test failure. The Test stage's post { always { ... } } block attempts to publish reports even when tests fail.

A Jenkins execution is not claimed unless an actual run has been performed.

8. Kubernetes Deployment Evidence

The deployment uses the nginx:1.27 image and initially requests two replicas. The NodePort Service selects Pods labeled app: nginx and exposes HTTP on NodePort 30080.

Recorded Minikube evidence showed:

Minikube node status: Ready
Initial Deployment: 2/2 replicas available
Service type: NodePort
Service endpoints: two Pod IP addresses on port 80
Scaled Deployment: 3/3 replicas available
Pod deletion: one existing Pod was deleted successfully
Self-healing: a replacement Pod was created
Final state: three Running Pods and three available replicas
Scaling and self-healing commands
kubectl scale deployment/nginx-deployment --replicas=3
kubectl rollout status deployment/nginx-deployment
kubectl get deployments
kubectl get pods -l app=nginx -o wide


Delete a real Pod name returned by kubectl get pods:

kubectl delete pod <actual-pod-name>
kubectl get pods -l app=nginx -w


The Deployment manages a ReplicaSet, which maintains the desired number of Pods. When a Pod is deleted, the controller creates a replacement to restore the requested replica count.

Manifest files
deployment.yaml — Nginx Deployment.
service.yaml — NodePort Service.

The manifests and genuine command outputs should be included in the submitted repository or evidence appendix.

9. Modified Files

Expected modified or added files include:

pom.xml
course-registration-app/pom.xml
course-registration-app/src/main/java/com/university/Student.java
course-registration-app/src/main/java/com/university/RegistrationService.java
course-registration-app/src/test/java/com/university/RegistrationServiceTest.java
Jenkinsfile
deployment.yaml
service.yaml
README.md

Confirm the actual file list using git show --stat and git status.

10. Limitations
A live Jenkins execution is not claimed unless it has been performed.
Minikube is a local development environment, not a production cluster.
Nginx is deployed to demonstrate Kubernetes Deployment, Service, scaling, and self-healing; it is not the Java application itself.
Only verified command outputs and genuine screenshots should be submitted.