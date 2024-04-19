package com.pbl.broker.Broker.models;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class ResponseModel {
    private int id;
    private String name;
    private String time;
    private String aliveTime;
    private String data;
    private String status;
}
