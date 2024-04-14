package com.pbl.broker.Broker;

import com.pbl.broker.Broker.services.DeviceService;
import com.pbl.broker.Broker.socket.SocketServer;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class BrokerApplication extends Thread{

	public static void main(String[] args) {
		// inicializa a aplicação springboot
		SpringApplication.run(BrokerApplication.class, args);

		// inicia o Broker em uma thread
		BrokerApplication thread = new BrokerApplication();
		thread.start();
	}

	public void run() {
		// inicia o servidor
		SocketServer.startServer();

		// recebe continuadamente mensagens dos clients
		new Thread(() -> {
			SocketServer.receiveMessage();
		}).start();

		SocketServer.waitClientsConnection();
	}
}
