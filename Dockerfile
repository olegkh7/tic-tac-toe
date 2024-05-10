FROM eclipse-temurin:17-alpine
VOLUME /tmp

COPY target/tic-tac-toe-game-0.0.1-SNAPSHOT.jar app.jar
ENTRYPOINT ["java","-jar","/app.jar"]