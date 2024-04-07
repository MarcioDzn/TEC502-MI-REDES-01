package com.pbl.broker.Broker;

import com.pbl.broker.Broker.repositories.RequestListRepository;
import com.pbl.broker.Broker.socket.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrokerApplication extends Thread{

	public static void main(String[] args) {
		// Inicia a aplicação Spring Boot
		SpringApplication.run(BrokerApplication.class, args);

		// Cria e inicia uma nova instância de BrokerApplication
		BrokerApplication thread = new BrokerApplication();
		thread.start();
	}

	public void run() {
		// Executa RequestListRepository.dispatchRequest() em uma thread separada
		new Thread(() -> {
			RequestListRepository.dispatchRequest();
		}).start();

		// inicia o servidor
		SocketServer.startServer();
		SocketServer.waitClientsConnection();


	}

}
