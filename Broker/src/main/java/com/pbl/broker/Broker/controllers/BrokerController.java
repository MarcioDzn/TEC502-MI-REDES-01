package com.pbl.broker.Broker.controllers;

import com.pbl.broker.Broker.models.SensorModel;
import com.pbl.broker.Broker.services.BrokerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/sensor")
public class BrokerController {
    BrokerService service = new BrokerService();

    @GetMapping("/{id}")
    public ResponseEntity<SensorModel> getSensorResponse(@PathVariable Long id) {
        List<String> response = service.getSensorResponse(id, "get_time");

        SensorModel sensor = new SensorModel(response.get(0), response.get(1));
        return ResponseEntity.ok(sensor);

        //String response = service.getSensorResponse(id, "get_time");
        //return ResponseEntity.ok().build();
    }
}
