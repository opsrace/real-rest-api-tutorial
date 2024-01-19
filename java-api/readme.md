# Run docker containers

`cd java-api`

`docker build -t java-api-example:latest .`

docker build -t japp .
docker run -it --rm japp

`docker run -p 8099:8080 --network=demo-network java-api-example:latest`
### Get container shell access
`docker exec -it containerId /bin/sh`

`docker compose up`

screen ~/Library/Containers/com.docker.docker/Data/com.docker.driver.amd64-linux/tty

an Alternate way to get into Docker linux virtual machine on macbook
`docker run -it --privileged --pid=host debian nsenter -t 1 -m -u -n -i sh`

## Mongodb
`docker run -p 27017:27017 -d -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=password --name mongodb-x --network=demo-network mongo`

`docker logs 311a0ea3bd0ff79920e7caf05786aa35253eb203db07a42f076e18b497fdb188`

`docker run -d -p 8081:8081 -e ME_CONFIG_MONGODB_ADMINUSERNAME=admin -e ME_CONFIG_MONGODB_ADMINPASSWORD=password --network demo-network --name demo-express -e ME_CONFIG_MONGODB_SERVER=mongodb-x mongo-express` 

## Run everything with Docker Compose
`docker-compose -f docker-compose.yaml up`

