FROM bellsoft/liberica-openjdk-alpine:17.0.5
LABEL maintainer="Ivan Vakhrushev"
ENV SPRING_PROFILES_ACTIVE=prod
COPY *.jar /usr/app/demo-app.jar
ENTRYPOINT ["java", "-jar", "/usr/app/demo-app.jar"]
