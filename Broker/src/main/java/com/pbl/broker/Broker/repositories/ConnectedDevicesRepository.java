package com.pbl.broker.Broker.repositories;

import java.net.Socket;
import java.util.HashMap;
import java.util.Map;

public class ConnectedDevicesRepository {
    private static Map<String, Socket> connectedDevices = new HashMap<>();

    public static void addDevice(String ip, Socket device) {
        connectedDevices.put(ip, device);
    }

    public static Socket getDevice(String ip) {
        return connectedDevices.get(ip);
    }
    public static void removeDevice(String ip) {
        connectedDevices.remove(ip);
    }
}
