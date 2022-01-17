FROM openjdk:11.0.12-jdk
COPY build/libs/auth.jar app.jar

ENTRYPOINT ["java", "-jar", "/app.jar"]