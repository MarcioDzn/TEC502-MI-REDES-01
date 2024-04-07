package com.pbl.broker.Broker.repositories;

import com.pbl.broker.Broker.models.RequestModel;
import com.pbl.broker.Broker.socket.SocketServer;

import java.util.LinkedList;
import java.util.List;

public class RequestListRepository {
    static List<RequestModel> queue = new LinkedList<>();

    public static void dispatchRequest() {
        while (true) {
            if (queue.size() > 0) {
                RequestModel request = queue.get(0);
                String ip = request.getIp();

                String message = request.getCommand() + " " + request.getContent();

                SocketServer.sendMessageToClient(ip, message);
                String response = SocketServer.receiveMessage(ip);
                ResponseRepository.addResponse(ip, response);

                queue.remove(0);
            }

            try {
                Thread.sleep(100);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }
    }

    public static void addToQueue(RequestModel request) {
        queue.add(request);
    }
}
