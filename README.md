# spring5-mvc-with-embedded-tomcat
Spring 5 MVC demo app with embedded Tomcat (without Spring Boot!) and OpenTracing

## Open in Browser
* http://localhost:8080/api/v1/demo
* http://localhost:8080/api/v1/demoException
* http://localhost:8080/api/v1/welcome

## Swagger
* http://localhost:8080/v2/api-docs

## Run in Docker
```
docker run --name spring5-mvc-with-embedded-tomcat -d -p 8080:8080 spring5-mvc-with-embedded-tomcat:1.0-SNAPSHOT
or
docker run --name spring5-mvc-with-embedded-tomcat -d -p 8080:8080 localhost:5000/spring5-mvc-with-embedded-tomcat:1.0-SNAPSHOT
```

## Run Jaeger in Docker
```
docker run -d --name jaeger -e COLLECTOR_ZIPKIN_HTTP_PORT=9411 -p 5775:5775/udp -p 6831:6831/udp -p 6832:6832/udp -p 5778:5778 -p 16686:16686 -p 14268:14268 -p 14250:14250 -p 9411:9411 jaegertracing/all-in-one:1.18
```

Jaeger UI will start at `http://localhost:16686`

## Useful Commands

```
# Create uber jar
.\gradlew clean shadowJar

# Run uber jar
java -jar build/libs/spring5-mvc-with-embedded-tomcat-1.0-SNAPSHOT.jar
or
mvn spring-boot:run
```

## Build Docker image

`mvn clean package docker:build`
