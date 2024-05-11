# Build and run

To build and run an application you can use `docker-compose up`. It will build an image of service
and start 2 services in separate docker containers and rabbitmq. Note, it may take a while to build an image.
Service 1 will be available via http://localhost:8081 and service 2 via http://localhost:8082.

# Start game
To start the game send empty post request to http://localhost:8081/game/start
To check status of the game - get request to http://localhost:8081/game/board
To restart the game send empty post request to http://localhost:8081/game/start
(Endpoints provided for the first instance, but you can use service 2 as well)

