FROM openjdk:17

COPY target/task-management-0.0.1-SNAPSHOT.jar task-app.jar

ENTRYPOINT ["java", "-jar", "task-app.jar"]