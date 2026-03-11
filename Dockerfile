FROM eclipse-temurin:21-jre-alpine
RUN mkdir /user_service
WORKDIR /user_service
COPY target/UserService-0.0.1-SNAPSHOT.jar /user_service
ENTRYPOINT java -jar /user_service/UserService-0.0.1-SNAPSHOT.jar