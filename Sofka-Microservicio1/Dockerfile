# --- Etapa de build (con tests) ---
FROM gradle:8.9-jdk17-alpine AS build
WORKDIR /home/gradle/project

# Copiamos wrapper y config primero para aprovechar cache
COPY gradlew settings.gradle build.gradle ./
COPY gradle gradle

# Copiamos el código
COPY src src
# Build args (injected at docker build time)
ARG DB_PRIMARY_DATASOURCE_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG JWT_SECRET
ARG JWT_EXPIRATION_MINUTES
ARG HASH_STRENGTH
ARG KEY_ID

# Environment variables (available to the app at runtime)
ENV DB_PRIMARY_DATASOURCE_URL=${DB_PRIMARY_DATASOURCE_URL}
ENV DB_USERNAME=${DB_USERNAME}
ENV DB_PASSWORD=${DB_PASSWORD}
ENV JWT_SECRET=${JWT_SECRET}
ENV JWT_EXPIRATION_MINUTES=${JWT_EXPIRATION_MINUTES}
ENV HASH_STRENGTH=${HASH_STRENGTH}
ENV KEY_ID=${KEY_ID}
RUN ./gradlew --no-daemon clean test bootJar

# --- Etapa de runtime (ligera) ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el .jar generado
COPY --from=build /home/gradle/project/build/libs/*-SNAPSHOT.jar /app/app.jar
# Si sabes el nombre final, mejor copia ese nombre exacto.

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]