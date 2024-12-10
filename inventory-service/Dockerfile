# 1️⃣ Image de base JDK 21
FROM eclipse-temurin:21-jdk

# 2️⃣ Répertoire de travail
WORKDIR /app

# 3️⃣ Téléchargez et ajoutez l'agent OpenTelemetry (avec cache optimisé)
RUN mkdir -p /app/otel && \
    curl -sL -o /app/otel/opentelemetry-javaagent.jar \
    https://github.com/open-telemetry/opentelemetry-java-instrumentation/releases/download/v1.28.0/opentelemetry-javaagent.jar && \
    ls -l /app/otel

# 4️⃣ Copier le JAR pré-construit (vérifiez le chemin)
COPY target/inventory-service-1.0.0-SNAPSHOT.jar /app/inventory-service.jar

# 5️⃣ Assurez-vous que le fichier JAR et l'agent ont les bonnes permissions
RUN chmod +x /app/inventory-service.jar && \
    chmod +x /app/otel/opentelemetry-javaagent.jar && \
    ls -l /app

# 6️⃣ Exposez le port 8082
EXPOSE 8082

# 7️⃣ Commande de démarrage
ENTRYPOINT ["java", "-javaagent:/app/otel/opentelemetry-javaagent.jar", "-jar", "/app/inventory-service.jar"]