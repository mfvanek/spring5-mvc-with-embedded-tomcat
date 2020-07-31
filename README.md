# spring5-mvc-with-embedded-tomcat
Spring 5 MVC demo app with embedded Tomcat (without Spring Boot!)

## Open in Browser
* http://localhost:8080/api/v1/demo
* http://localhost:8080/api/v1/demoException
* http://localhost:8080/api/v1/welcome

## Swagger
* http://localhost:8080/v2/api-docs

## Run in Docker
```
docker run --name spring5-mvc-with-embedded-tomcat -d -p 8080:8080 spring5-mvc-with-embedded-tomcat:1.0-SNAPSHOT
```

## Useful Commands

```
# Create uber jar
.\gradlew clean shadowJar

# Run uber jar
java -jar build/libs/spring5-mvc-with-embedded-tomcat-1.0-SNAPSHOT.jar
```
