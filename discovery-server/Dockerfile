FROM fabric8/java-alpine-openjdk11-jdk

LABEL maintainer="Sparky Studios <development@traklibrary.com>"

WORKDIR app

# Copy the jar file into the docker container.
ARG jar=target/traklibrary-discovery-api.jar
COPY ${jar} traklibrary-discovery-api.jar

# Expose this port for external communication.
EXPOSE 8761

ENTRYPOINT ["java", "-Djava.security.egd-file=file:/dev/./urandom", "-jar", "traklibrary-discovery-api.jar"]

