# votetest

Running standalone spring boot go to project root directory and execute
mvn package && java -jar target/votetest-0.0.1-SNAPSHOT.jar
note that it runs on port 8888

{project.root}/url-test.sh - executes some requests against the spring boot application

Project structure:
- src/main - sources of the project
  - src/main/java/com/votetest/model - data objects
  - src/main/java/com/votetest/dao - data access objects
  - src/main/java/com/votetest/model - business services
  - src/main/java/com/votetest/contoller - rest controllers to expose api on the web
- src/test - test of the system components. Each component dao, service, controller has its own tests
