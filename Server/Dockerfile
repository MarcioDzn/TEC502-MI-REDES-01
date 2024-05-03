# Usa uma imagem base Python
FROM python:3.8.10-slim

# Copia o script Python para o contêiner
COPY . /app

# Define o diretório de trabalho
WORKDIR /app

# Define a variável de ambiente padrão
ENV BROKER_IP="localhost"

EXPOSE 3002

# Comando para executar o script Python
CMD ["python", "main.py"]