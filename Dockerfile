FROM gradle:7.6-jdk AS build
ENV APP_HOME=/app/
WORKDIR $APP_HOME
COPY build.gradle settings.gradle $APP_HOME
COPY src $APP_HOME/src/
RUN gradle build

# Run stage
FROM openjdk:17-oracle
ENV APP_HOME=/app/
ENV ARTIFACT_NAME=task2-application-1.0-SNAPSHOT.jar
ARG JAR_FILE=$APP_HOME/build/libs/$ARTIFACT_NAME
COPY --from=build $JAR_FILE /opt/task/app.jar
ENTRYPOINT ["java", "-jar", "/opt/task/app.jar"]


#FROM openjdk:17-oracle
#ENV APP_HOME=/app/
#ENV ARTIFACT_NAME=task2-application-1.0-SNAPSHOT.jar
#
#WORKDIR /opt/task
#
#RUN curl -O https://jdbc.postgresql.org/download/postgresql-42.2.20.jar
#RUN mv postgresql-42.2.20.jar /opt/task/lib
#
#COPY --from=build $APP_HOME/build/libs/$ARTIFACT_NAME /opt/task/app.jar
#
#ENV CLASSPATH=/opt/task/lib/postgresql-42.2.20.jar:$CLASSPATH
#
#ENTRYPOINT ["java", "-jar", "/opt/task/app.jar"]