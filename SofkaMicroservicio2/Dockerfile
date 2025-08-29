# --- Build stage (runs tests) ---
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

# 1) Gradle wrapper & config (cache-friendly)
COPY gradlew gradlew.bat ./
COPY gradle/ ./gradle/
RUN chmod +x gradlew

# 2) Build scripts + sources
COPY settings.gradle build.gradle ./
COPY src ./src

# 3) (Optional) test-only env via build args
ARG DB_PRIMARY_DATASOURCE_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG MS1_URL
ARG ISSUER_URI

# Make them available during tests ONLY (not baked into final image)
ENV DB_PRIMARY_DATASOURCE_URL=$DB_PRIMARY_DATASOURCE_URL \
    DB_USERNAME=$DB_USERNAME \
    DB_PASSWORD=$DB_PASSWORD \
    MS1_URL=$MS1_URL \
    ISSUER_URI=$ISSUER_URI

# 4) Build & test
RUN ./gradlew --no-daemon clean test bootJar

# --- Runtime stage (slim) ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copy the boot jar from the CORRECT path
# If your jar is not SNAPSHOT, change the glob accordingly.
COPY --from=build /workspace/build/libs/*-SNAPSHOT.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]