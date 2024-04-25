# Usa a imagem oficial do OpenJDK 17 como base
FROM openjdk:17-alpine

# Define o diretório de trabalho no contêiner
WORKDIR /app

# Copia o arquivo pom.xml para o diretório de trabalho
COPY pom.xml .

# Copia todo o código fonte para o diretório de trabalho
COPY src ./src

# Instalação do Maven
RUN apk --no-cache add maven

# Executa o comando Maven para compilar o projeto
RUN mvn clean package

# Expõe a porta em que o aplicativo será executado (se necessário)
EXPOSE 8080

# Comando para executar a aplicação Spring Boot quando o contêiner for iniciado
CMD ["java", "-jar", "target/Broker-0.0.1-SNAPSHOT.jar"]