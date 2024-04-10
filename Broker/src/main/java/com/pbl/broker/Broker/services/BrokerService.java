package com.pbl.broker.Broker.services;

import com.pbl.broker.Broker.models.ResponseModel;
import com.pbl.broker.Broker.repositories.ResponseRepository;
import com.pbl.broker.Broker.socket.SocketServer;

import java.util.List;

public class BrokerService {
    public ResponseModel getSensorContinuousResponse() {
        ResponseModel response = ResponseRepository.getResponse("127.0.0.1");

        return response;
    }

    public void sendSensorReq(String command) {
        SocketServer.sendMessageToClient("127.0.0.1", command);

    }
}
