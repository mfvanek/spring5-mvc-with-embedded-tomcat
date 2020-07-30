FROM adoptopenjdk/openjdk11:ubi-jre
ENV SPRING_PROFILES_ACTIVE=prod
COPY build/libs/*-all.jar /usr/app/demo-app.jar
ENTRYPOINT ["java", "-jar", "/usr/app/demo-app.jar"]
