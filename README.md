# spring5-mvc-with-embedded-tomcat
Spring 5 MVC demo app with embedded Tomcat (without Spring Boot!) and OpenTracing

## Open in Browser
* http://localhost:8080/api/v1/demo
* http://localhost:8080/api/v1/demoException
* http://localhost:8080/api/v1/welcome

## Swagger
* http://localhost:8080/v2/api-docs
* http://localhost:8080/swagger-ui/
* http://localhost:8080/swagger-ui/index.html

## Build Docker image
```shell
./gradlew dockerBuildImage
```

## Run in Docker
```shell
docker run --name spring5-mvc-with-embedded-tomcat -d -p 8080:8080 spring5-mvc-with-embedded-tomcat:latest
```

## Run Jaeger in Docker
```shell
docker run -d --name jaeger \
  -e COLLECTOR_ZIPKIN_HOST_PORT=:9411 \
  -e COLLECTOR_OTLP_ENABLED=true \
  -p 6831:6831/udp \
  -p 6832:6832/udp \
  -p 5778:5778 \
  -p 16686:16686 \
  -p 4317:4317 \
  -p 4318:4318 \
  -p 14250:14250 \
  -p 14268:14268 \
  -p 14269:14269 \
  -p 9411:9411 \
  jaegertracing/all-in-one:1.36
```

Jaeger UI will start at [http://localhost:16686](http://localhost:16686)

## Useful Commands
### Create uber jar
```shell
./gradlew shadowJar
```

### Run uber jar
```shell
# export JAEGER_AGENT_HOST=localhost
# export JAEGER_AGENT_PORT=6831
export JAEGER_ENDPOINT=http://localhost:14268/api/traces
java -jar build/libs/spring5-mvc-with-embedded-tomcat-2.0.0.jar
```
