FROM openjdk:21-jdk-slim

WORKDIR /app

# Copia el .jar de tu aplicación
COPY target/microservicio-usuarios-mascotas-0.0.1-SNAPSHOT.jar app.jar

# Copia el wallet comprimido
COPY Wallet_G76VPGAYD2UU9GM4.zip /app/

# Instala unzip, descomprime el wallet y lo elimina
RUN apt-get update && apt-get install -y unzip && \
    unzip Wallet_G76VPGAYD2UU9GM4.zip -d /app/oracle_wallet && \
    rm Wallet_G76VPGAYD2UU9GM4.zip

EXPOSE 8080

CMD ["java", "-jar", "app.jar"]
