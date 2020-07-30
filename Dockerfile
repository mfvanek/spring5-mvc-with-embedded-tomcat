FROM adoptopenjdk/openjdk11:ubi-jre
ENV SPRING_PROFILES_ACTIVE=prod
COPY *-all.jar /usr/app/demo-app.jar
ENTRYPOINT ["java", "-jar", "/usr/app/demo-app.jar"]
