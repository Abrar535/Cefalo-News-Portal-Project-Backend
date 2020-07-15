FROM openjdk:14
ADD target/docker-backend.jar docker-backend.jar
EXPOSE 8081
ENTRYPOINT ["java", "-jar", "docker-backend.jar"]