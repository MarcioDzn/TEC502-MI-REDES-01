package com.pbl.broker.Broker.socket;

import com.pbl.broker.Broker.models.ResponseModel;
import com.pbl.broker.Broker.repositories.ConnectedDevicesRepository;
import com.pbl.broker.Broker.repositories.ResponseRepository;
import com.pbl.broker.Broker.utils.ResponseSplit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.List;
import java.util.Map;

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
            System.out.println("Servidor UDP iniciado na porta 5000.");
        } catch (IOException e) {
            throw new RuntimeException("Erro ao inicializar o servidor.", e);
        }
    }

    // singleton
    public static SocketServer getInstance() {
        if (instance == null) {
            instance = new SocketServer();
        }
        return instance;
    }

    public static void startServer() {
        getInstance();
    }

    // armazena os dispositivos que se conectaram ao servidor
    public static void waitClientsConnection() {
        while (true) {
            Socket client = null;
            try {
                client = tcpServer.accept();
                System.out.println("Dispositivo conectado do IP " + client.getInetAddress().getHostAddress());
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

    // recebe de maneira contínua mensagens dos clients e as armazena em um HashMap
    public static void receiveMessage() {
        byte[] buffer = new byte[1024];
        DatagramPacket packet = new DatagramPacket(buffer, buffer.length);

        while (true) {
            try {
                udpServer.receive(packet);

                // cada mensagem recebida é processada em uma thread específica
                new Thread(() -> {
                    String message = new String(packet.getData(), 0, packet.getLength());
                    String senderIp = packet.getAddress().getHostAddress();
                    int id = ConnectedDevicesRepository.getIdByDevice(senderIp);

                    Map<String, String> responseSplitted = ResponseSplit.splitResponse(message);

                    String responseType = responseSplitted.get("type");

                    if (responseType.equals("data")) {
                        ResponseModel response = new ResponseModel(id, responseSplitted.get("name"), responseSplitted.get("time"), responseSplitted.get("time"),  responseSplitted.get("data"), responseSplitted.get("status"));

                        ResponseRepository.addResponse(senderIp, response);

                    } else if (responseType.equals("alive_check")) {
                        ResponseModel response = ResponseRepository.getResponse(senderIp);

                        if (response != null) {
                            response.setAliveTime(responseSplitted.get("time"));

                        } else {
                            response = new ResponseModel(id, responseSplitted.get("name"), responseSplitted.get("time"), responseSplitted.get("time"), "offline", "offline");
                        }

                        ResponseRepository.addResponse(senderIp, response);
                    }

                }).start();

            } catch (SocketException e) {
                throw new RuntimeException(e);

            } catch (IOException e) {
                throw new RuntimeException(e);
            }
        }
    }
}