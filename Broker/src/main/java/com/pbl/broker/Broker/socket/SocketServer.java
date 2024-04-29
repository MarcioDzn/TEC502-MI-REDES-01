package com.pbl.broker.Broker.socket;

import com.pbl.broker.Broker.models.DeviceModel;
import com.pbl.broker.Broker.models.ResponseModel;
import com.pbl.broker.Broker.repositories.DevicesRepository;
import com.pbl.broker.Broker.repositories.ResponseRepository;
import com.pbl.broker.Broker.utils.ResponseSplit;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.net.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class SocketServer {
    private static DatagramSocket udpServer;
    private static SocketServer instance;

    private SocketServer() {
        try {
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
    public static void sendDeviceTCPConnection() throws InterruptedException {
        while (true) {
            Map<String, DeviceModel> devices = DevicesRepository.getConnectedDevices();
            List<String> deviceIpList = new ArrayList<>(devices.keySet());
    
            for (int i = 0; i < devices.size(); i++) {
                DeviceModel device = devices.get(deviceIpList.get(i));
                
                // tenta estabelecer uma conexão TCP com os dispositivos que ainda não estão conectados
                if (!device.isConnected()) {
                    try {
                        sendMessageToClient(device.getIp(), device.getPort(), "first_conn " + device.getIp());
                        device.setConnected(true);
                        System.out.println("Dispositivo do ip " + device.getIp() + " conectado com sucesso!");
    
                    } catch (UnknownHostException e) {
                        System.out.println("Não foi possível conectar-se ao dispositivo de ip: " + device.getIp());
                    } catch (IOException e ) {
                        e.printStackTrace();
                    }
                }
            }

            Thread.sleep(5000);
        }

    }

    public static void sendMessageToClient(String ip, int port, String message) throws UnknownHostException, IOException {
        Socket socket = new Socket(ip, port);
        System.out.println("Conexão estebelecida com sucesso!\nEnviando mensagem!");
        PrintWriter out = new PrintWriter(socket.getOutputStream(), true);

        out.println(message);
        
        out.close();
        socket.close();
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
                    int id = DevicesRepository.getIdByDevice(senderIp);

                    Map<String, String> responseSplitted = ResponseSplit.splitResponse(message);

                    String responseType = responseSplitted.get("type");
                    

                    if (responseType.equals("data")) {
                        ResponseModel response = new ResponseModel(id, responseSplitted.get("name"), responseSplitted.get("ip"), responseSplitted.get("time"), responseSplitted.get("time"),  responseSplitted.get("data"), responseSplitted.get("status"));

                        ResponseRepository.addResponse(responseSplitted.get("ip"), response);

                    } else if (responseType.equals("alive_check")) {
                        ResponseModel response = ResponseRepository.getResponse(senderIp);

                        if (response != null) {
                            response.setAliveTime(responseSplitted.get("time"));

                        } else {
                            response = new ResponseModel(id, responseSplitted.get("name"), responseSplitted.get("ip"), responseSplitted.get("time"), responseSplitted.get("time"), "offline", "offline");
                        }

                        ResponseRepository.addResponse(responseSplitted.get("ip"), response);
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