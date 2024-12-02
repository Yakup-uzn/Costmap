# Java 17 tabanlı resmi Docker imajını kullanıyoruz
FROM eclipse-temurin:17-jdk-alpine

# Uygulama dizinini ayarla
WORKDIR /app

# Maven tarafından oluşturulan JAR dosyasını konteynıra kopyala
COPY target/map-0.0.1-SNAPSHOT.jar app.jar


# Uygulamayı çalıştır
ENTRYPOINT ["java", "-jar", "app.jar"]
