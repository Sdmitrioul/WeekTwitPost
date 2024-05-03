FROM openjdk:17
ADD target/posts-0.0.1-SNAPSHOT.jar posts-0.0.1-SNAPSHOT.jar
ENTRYPOINT ["java", "-jar","posts-0.0.1-SNAPSHOT.jar"]
EXPOSE 8082