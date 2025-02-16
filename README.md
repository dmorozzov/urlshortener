# Readme
This is mvp short url generator project.

### Assumptions:
* Service layer is missing unit tests.
* Integration tests are not provided.
* Base62 encoding is used for encoding of unique identifiers. 
  In the mvp the unique identifiers are provided by database. In the production distributed system proper unique id generator has to be used e.g. Ticket service / Twitter's Snowflake.

### Run locally:
```bash
  ./start-dependencies.sh
  ./gradlew bootRun
```
or
```bash
  docker compose -f docker-compose.yml up -d
```

## Api documentation
* Swagger UI: http://localhost:8080/swagger
* Api docs: http://localhost:8080/api-docs

## Prepare container for deployment
### Build project
```bash
  ./gradlew clean build
```
### Build docker image
```bash
  docker build -t urlshortener .
```
### Run container
```bash
  docker container run -p 8080:8080 \
    -e REDIS_HOST='<REDIS_HOST>' \
    -e REDIS_PORT='6379' \
    --name urlshortener urlshortener:latest
```