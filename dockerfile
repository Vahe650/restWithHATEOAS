From openjdk:8
copy ./target/rest-0.0.1-SNAPSHOT.jar rest-0.0.1-SNAPSHOT.jar
CMD ["java","-jar","rest-0.0.1-SNAPSHOT.jar"]
