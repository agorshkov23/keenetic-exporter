FROM alpine:3.18.0 as builder

RUN apk add --no-cache maven openjdk17-jdk
# RUN apk add --no-cache tree

# WORKDIR /app
# COPY pom.xml src/ /app/

# # RUN --mount=type=cache,target=/root/.m2,rw mvn --help
# RUN --mount=type=cache,target=/root/.m2,rw mvn --batch-mode package

# # RUN mvn --batch-mode package

# # RUN cp target/keenetic-exporter-1.0.0-SNAPSHOT.jar app.jar

# # CMD ["java", "-jar", "/app/target/keenetic-exporter-1.0.0-SNAPSHOT.jar"]
# RUN tree /app/target
# # CMD ls -la
# # CMD java -jar app.jar


# FROM maven:3.9.6-eclipse-temurin-17-alpine as builder
# FROM maven:3.9.6-eclipse-temurin-21-alpine as builder

WORKDIR /app
COPY pom.xml src/ /app/
# RUN --mount=type=cache,target=/root/.m2,rw mvn --batch-mode package


FROM alpine:3.18.0
WORKDIR /app
RUN apk add --no-cache openjdk17-jre-headless

ENV SPRING_CONFIG_ADDITIONAL_LOCATION=conf/

# COPY --from=builder /app/target/keenetic-exporter-*-exec.jar /app/app.jar
COPY target/keenetic-exporter-*-exec.jar /app/app.jar

# RUN cat app.jar | head -n 10

CMD java -jar app.jar
