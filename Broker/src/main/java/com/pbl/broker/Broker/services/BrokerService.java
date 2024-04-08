package com.pbl.broker.Broker.services;

import com.pbl.broker.Broker.models.RequestModel;
import com.pbl.broker.Broker.repositories.ConnectedDevicesRepository;
import com.pbl.broker.Broker.repositories.RequestListRepository;
import com.pbl.broker.Broker.repositories.ResponseRepository;
import com.pbl.broker.Broker.socket.SocketServer;

import java.util.List;

public class BrokerService {
    public List<String> sendRequest(Long id, String command) {
        String response;
        if (ConnectedDevicesRepository.getDevice("127.0.0.1") == null) {
            response = command + " " + "Dispositivo_não_encontrado";
        } else {
            RequestListRepository.addToQueue(new RequestModel("127.0.0.1", command, "Content"));
            response = command + " enviado!";
        }

        return List.of(response.split(" "));
    }

    public List<String> getSensorResponse(Long id) {
        String response = ResponseRepository.getResponse("127.0.0.1");

        System.out.println(response);
        if (response == null) {
            response = "a error";

        } else if (response.equals("error")) {
            ConnectedDevicesRepository.removeDevice("127.0.0.1");
            response = "a error";
        }

        return List.of(response.split(" "));
    }
}
