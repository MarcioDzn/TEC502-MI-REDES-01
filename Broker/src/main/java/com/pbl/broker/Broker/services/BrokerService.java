package com.pbl.broker.Broker.services;

import com.pbl.broker.Broker.models.ResponseModel;
import com.pbl.broker.Broker.repositories.ResponseRepository;
import com.pbl.broker.Broker.socket.SocketServer;

import java.util.List;

public class BrokerService {
    public List<ResponseModel> getAllSensorContinuousResponse() {
        List<ResponseModel> response = ResponseRepository.getAllResponse();

        return response;
    }

    public ResponseModel getSensorContinuousResponse(Long id) {
        ResponseModel response = ResponseRepository.getResponseById(id);

        return response;
    }

    public void sendSensorReq(Long id, String command) {
        String address = ResponseRepository.getKeyItem(id);

        SocketServer.sendMessageToClient(address, command);

    }
}
