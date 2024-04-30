package com.pbl.broker.Broker.services;

import com.pbl.broker.Broker.models.DeviceModel;
import com.pbl.broker.Broker.models.ResponseModel;
import com.pbl.broker.Broker.repositories.DevicesRepository;
import com.pbl.broker.Broker.repositories.ResponseRepository;
import com.pbl.broker.Broker.socket.SocketServer;

import java.io.IOException;
import java.net.UnknownHostException;
import java.net.SocketTimeoutException;
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

    public void sendSensorReq(Long id, String command) throws IOException, SocketTimeoutException {
        String ip = ResponseRepository.getKeyItem(id);
        DeviceModel device = DevicesRepository.getDevice(ip);

        SocketServer.sendMessageToClient(device.getIp(), device.getPort(), command);
    }

    public void addDevice(String ip, int port) {
        DeviceModel device = new DeviceModel(ip, port, false);
    
        DevicesRepository.addDevice(ip, device);
    }
}
