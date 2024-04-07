package com.pbl.broker.Broker.repositories;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ResponseRepository {
    private static Map<String, String> responses = new HashMap<>();

    public static void addResponse(String ip, String response) {
        responses.put(ip, response);
    }

    public static String getResponse(String ip) {
        String response = responses.get(ip);

        // a response deve ser removida da lista quando é buscada
        removeResponse(ip);

        return response;
    }

    public static void removeResponse(String ip) {
        responses.remove(ip);
    }
}
