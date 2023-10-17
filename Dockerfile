FROM gradle:8.4.0-alpine AS build
ENV APP_HOME=/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME
COPY src $APP_HOME/src/
RUN gradle clean build -x test


FROM gradle:8.4.0-alpine AS test
ENV APP_HOME=/app/
WORKDIR $APP_HOME
COPY --from=build $APP_HOME $APP_HOME
RUN gradle test


FROM openjdk:17-oracle
ENV APP_HOME=/app/
ENV ARTIFACT_NAME=task2-application-1.0-SNAPSHOT.jar
ARG JAR_FILE=$APP_HOME/build/libs/$ARTIFACT_NAME
COPY --from=build $JAR_FILE /opt/task/app.jar
ENTRYPOINT ["java", "-jar", "/opt/task/app.jar"]