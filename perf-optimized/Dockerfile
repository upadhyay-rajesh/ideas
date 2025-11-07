# Build stage
FROM maven:3.9-eclipse-temurin-17 as builder
WORKDIR /workspace
COPY . .
RUN mvn -q -DskipTests package

# Runtime stage
FROM eclipse-temurin:17-jre
WORKDIR /app
COPY --from=builder /workspace/target/perf-optimized-0.0.1-SNAPSHOT.jar app.jar
ENV JAVA_OPTS="-XX:+UseG1GC -XX:MaxGCPauseMillis=100"
EXPOSE 8080
ENTRYPOINT ["sh","-c","java $JAVA_OPTS -jar app.jar"]
