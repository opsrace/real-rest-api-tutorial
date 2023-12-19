# Run docker containers

`docker build -t java-api-example:latest .`
docker build -t japp .
docker run -it --rm japp

`docker run -p 8099:8080 --network=demo-network java-api-example:latest`
## Mongodb
`docker run -p 27017:27017 -d -e MONGO_INITDB_ROOT_USERNAME=admin -e MONGO_INITDB_ROOT_PASSWORD=password --name mongodb-x --network=demo-network mongo`

`docker logs 311a0ea3bd0ff79920e7caf05786aa35253eb203db07a42f076e18b497fdb188`

`docker run -d -p 8081:8081 -e ME_CONFIG_MONGODB_ADMINUSERNAME=admin -e ME_CONFIG_MONGODB_ADMINPASSWORD=password --network demo-network --name demo-express -e ME_CONFIG_MONGODB_SERVER=mongodb-x mongo-express` 