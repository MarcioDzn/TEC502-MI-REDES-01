package com.pbl.broker.Broker.utils;

import java.util.HashMap;
import java.util.Map;

public class ResponseSplit {
    public static Map<String, String> splitResponse(String response) {
        Map<String, String> responseSplitted = new HashMap<>();

        String[] pairs = response.split(", ");

        for (int i = 0; i < pairs.length; i++){
            String[] keyValue = pairs[i].split("::");

            if (keyValue.length == 2) {
                String key = keyValue[0];
                String value = keyValue[1];

                responseSplitted.put(key, value);
            }
        }

        return responseSplitted;
    }
}
