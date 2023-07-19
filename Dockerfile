FROM openjdk:17-alpine
WORKDIR /apps
COPY controller/target/controller-0.0.1-SNAPSHOT.war /apps/springboot-docker.war
ENV SPRING_PROFILES_ACTIVE=prod
ENTRYPOINT ["java", "-jar", "springboot-docker.war"]
