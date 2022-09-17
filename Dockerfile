FROM openjdk:11
COPY target/tweet-app-backend.jar app.jar
ENTRYPOINT ["java", "-jar", "/app.jar"]