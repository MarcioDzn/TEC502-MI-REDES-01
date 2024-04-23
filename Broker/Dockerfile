# Use uma imagem base com o Maven e o JDK 17 instalados
FROM maven:3.8.7-openjdk-17 AS builder

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie apenas o arquivo pom.xml
COPY pom.xml .

# Baixe as dependências do Maven
RUN mvn dependency:go-offline

# Copie o restante do código-fonte
COPY src ./src

# Compile o código-fonte
RUN mvn clean package

# Use uma imagem base com o JRE (Java Runtime Environment) 17
FROM adoptopenjdk:17-jre AS final

# Defina o diretório de trabalho no contêiner
WORKDIR /app

# Copie o arquivo JAR construído a partir da imagem de compilação
COPY --from=builder /app/target/Broker-0.0.1-SNAPSHOT.jar .

# Exponha a porta em que a aplicação é executada
EXPOSE 8080

# Comando para executar a aplicação quando o contêiner for iniciado
CMD ["java", "-jar", "Broker-0.0.1-SNAPSHOT.jar"]