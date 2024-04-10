package com.pbl.broker.Broker.repositories;

import com.pbl.broker.Broker.models.ResponseModel;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ResponseRepository {
    private static Map<String, ResponseModel> responses = new HashMap<>();

    public static void addResponse(String ip, ResponseModel response) {
        responses.put(ip, response);
    }

    public static ResponseModel getResponse(String ip) {
        ResponseModel response = responses.get(ip);

        return response;
    }

    public static void removeResponse(String ip) {
        responses.remove(ip);
    }
}
