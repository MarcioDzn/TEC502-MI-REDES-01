package com.pbl.broker.Broker.repositories;

import com.pbl.broker.Broker.models.ResponseModel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ResponseRepository {
    private static Map<String, ResponseModel> responses = new HashMap<>();

    public static void addResponse(String ip, ResponseModel response) {
        responses.put(ip, response);
    }

    public static List<ResponseModel>  getAllResponse() {
        return new ArrayList<>(responses.values());
    }

    public static ResponseModel getResponse(String ip) {
        ResponseModel response = responses.get(ip);

        return response;
    }

    public static ResponseModel getResponseById(Long id) {
        List<ResponseModel> items = new ArrayList<>(responses.values());
        return items.get(id.intValue());
    }

    public static String getKeyItem(Long id) {
        List<String> items = new ArrayList<>(responses.keySet());
        return items.get(id.intValue());
    }

    public static List<String> getIpKeys() {
        List<String> items = new ArrayList<>(responses.keySet());
        return items;
    }

    public static void removeResponse(String ip) {
        responses.remove(ip);
    }

}
