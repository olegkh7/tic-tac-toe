FROM maven:3.9.6-eclipse-temurin-17-alpine AS build

WORKDIR /app
COPY . .

RUN mvn clean package


FROM eclipse-temurin:17-alpine

COPY --from=build /app/target/tic-tac-toe-game-0.0.1-SNAPSHOT.jar app.jar

ENTRYPOINT ["java","-jar","/app.jar"]


