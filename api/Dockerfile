# To refresh the image, regenerate the JAR by using:
# mvn clean package -o

FROM adoptopenjdk/openjdk16:alpine

COPY ./target/todo-list-0.0.1-SNAPSHOT.jar todo-list.jar

CMD ["java", "-jar", "todo-list.jar"]
