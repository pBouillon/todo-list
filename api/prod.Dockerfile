# Build container
FROM maven:3-openjdk-11 AS BUILDER

COPY ./ ./

RUN mvn clean package

# Runtime environment container
FROM openjdk:11-jre

COPY --from=BUILDER ./target/todo-list-0.0.1-SNAPSHOT.jar todo-list.jar

CMD ["java", "-jar", "todo-list.jar"]
