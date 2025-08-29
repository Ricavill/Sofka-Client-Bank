# --- Etapa de build (con tests) ---
FROM eclipse-temurin:17-jdk AS build
WORKDIR /workspace

# Wrapper primero (cache)
COPY gradlew gradlew.bat ./
COPY gradle/wrapper/ ./gradle/wrapper/
RUN chmod +x gradlew

# Archivos de build (cache-friendly)
COPY settings.gradle build.gradle ./
# (Opcional) si tienes gradle.properties o libs.versions.toml:
# COPY gradle.properties ./
# COPY gradle/libs.versions.toml ./gradle/

# Código fuente
COPY src ./src

# Build args para tests en build stage (NO quedan en runtime)
ARG DB_PRIMARY_DATASOURCE_URL
ARG DB_USERNAME
ARG DB_PASSWORD
ARG JWT_SECRET
ARG JWT_EXPIRATION_MINUTES
ARG HASH_STRENGTH
ARG KEY_ID

# Disponibles durante el RUN de gradle (tests)
ENV DB_PRIMARY_DATASOURCE_URL=$DB_PRIMARY_DATASOURCE_URL \
    DB_USERNAME=$DB_USERNAME \
    DB_PASSWORD=$DB_PASSWORD \
    JWT_SECRET=$JWT_SECRET \
    JWT_EXPIRATION_MINUTES=$JWT_EXPIRATION_MINUTES \
    HASH_STRENGTH=$HASH_STRENGTH \
    KEY_ID=$KEY_ID

# Build & tests
RUN ./gradlew --no-daemon clean test bootJar

# --- Etapa de runtime (ligera) ---
FROM eclipse-temurin:17-jre-alpine
WORKDIR /app

# Copiamos el .jar desde la ruta correcta
# Ajusta el patrón si no es SNAPSHOT
COPY --from=build /workspace/build/libs/*.jar /app/app.jar

EXPOSE 8080
ENTRYPOINT ["java","-jar","/app/app.jar"]