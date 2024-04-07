package com.pbl.broker.Broker.socket;

import com.pbl.broker.Broker.repositories.ConnectedDevicesRepository;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;

public class SocketServer {
    private static ServerSocket tcpServer;
    private static DatagramSocket udpServer;
    private static SocketServer instance;

    private SocketServer() {
        try {
            InetSocketAddress address = new InetSocketAddress("0.0.0.0", 3000);
            tcpServer = new ServerSocket();
            tcpServer.bind(address);

            System.out.println("Servidor " + InetAddress.getLocalHost() + " TCP iniciado na porta 3000.");

            udpServer = new DatagramSocket(5000);
            udpServer.setSoTimeout(1000);
            System.out.println("Servidor UDP iniciado na porta 5000.");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao inicializar o servidor.", e);
        }
    }

    public static SocketServer getInstance() {
        if (instance == null) {
            instance = new SocketServer();
        }
        return instance;
    }

    public static void startServer() {
        getInstance();
    }

    public static void waitClientsConnection() {
        while (true) {
            Socket client = null;
            try {
                client = tcpServer.accept();
                System.out.println("Cliente conectado do IP " + client.getInetAddress().getHostAddress());
                ConnectedDevicesRepository.addDevice(client.getInetAddress().getHostAddress(), client);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public static void sendMessageToClient(String clientIdentifier, String message) {
        Socket client = ConnectedDevicesRepository.getDevice(clientIdentifier);
        if (client != null) {
            try {
                OutputStream outputStream = client.getOutputStream();
                PrintWriter out = new PrintWriter(outputStream, true);
                out.println(message);
            } catch (IOException e) {
                throw new RuntimeException("Erro ao enviar mensagem para o cliente.", e);
            }
        } else {
            System.out.println("Cliente não encontrado: " + clientIdentifier);
        }
    }

    public static String receiveMessage(String ip) {
        while (true) {
            try {

                byte[] buffer = new byte[1024];
                DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

                udpServer.receive(packet);
                String message = new String(packet.getData(), 0, packet.getLength());

                return message;

            } catch (SocketException e) {
                throw new RuntimeException(e);

            } catch (SocketTimeoutException e) {

                // remove da lista de dispositivos conectados se passar muito tempo sem responder
                return "error";

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}
