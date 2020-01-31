# Use the official maven/Java 8 image to create a build artifact.
# https://hub.docker.com/_/maven
FROM maven:3.5-jdk-8-alpine as builder

# Copy local code to the container image.
WORKDIR /app
COPY pom.xml .
COPY src ./src
## サービスアカウントファイルのPathまたはCopyの方法は別途設計した方がいい
## サービスアカウントファイルをDockerFileと同じPathで配置してください。
## サービスアカウントファイルCopy
COPY spanner-service-account.json .

# Build a release artifact.
RUN mvn package -DskipTests

# Use AdoptOpenJDK for base image.
# It's important to use OpenJDK 8u191 or above that has container support enabled.
# https://hub.docker.com/r/adoptopenjdk/openjdk8
# https://docs.docker.com/develop/develop-images/multistage-build/#use-multi-stage-builds
FROM adoptopenjdk/openjdk8:jdk8u202-b08-alpine-slim

# Copy the jar to the production image from the builder stage.
COPY --from=builder /app/target/spanner-*.jar /spanner.jar

# copy WAR into image
COPY --from=builder /app/spanner-service-account.json /tmp/spanner-service-account.json
# run application with this command line
CMD ["java", "-jar", "-Dspring.profiles.active=default", "/spanner.jar"]