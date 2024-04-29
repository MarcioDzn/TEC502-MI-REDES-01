package com.pbl.broker.Broker.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;


@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class DeviceModel {
    public String ip;
    public int port;
    public boolean isConnected;


}
