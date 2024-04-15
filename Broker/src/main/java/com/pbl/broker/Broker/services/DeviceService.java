package com.pbl.broker.Broker.services;

import com.pbl.broker.Broker.models.ResponseModel;
import com.pbl.broker.Broker.repositories.ResponseRepository;

import java.time.Duration;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.List;

public class DeviceService {
    // torna todos os dispositivos "desconectados" a cada n segundos
    public static void invalidateDeviceConnection() throws InterruptedException {

        while (true) {
            List<String> deviceIps = ResponseRepository.getIpKeys();
            LocalTime currentTime = LocalTime.now();

            for (int i = 0; i < deviceIps.size(); i++) {
                ResponseModel deviceResponse = ResponseRepository.getResponse(deviceIps.get(i));

                if (deviceResponse == null) {
                    break;
                }

                LocalTime responseTime = LocalTime.parse(deviceResponse.getTime());

                Long timeDiff = Math.abs(Duration.between(currentTime, responseTime).getSeconds());

                if (timeDiff > 2 && (!deviceResponse.getStatus().equals("offline") && !deviceResponse.getStatus().equals("paused"))){
                    deviceResponse.setStatus("disconnected");
                }
            }

            Thread.sleep(2000);
        }
    }
}
