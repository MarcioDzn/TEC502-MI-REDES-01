package com.pbl.broker.Broker.controllers;

import com.pbl.broker.Broker.models.SensorModel;
import com.pbl.broker.Broker.services.BrokerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor")
public class BrokerController {
    BrokerService service = new BrokerService();

    @PostMapping("/{id}")
    public ResponseEntity<SensorModel> sendRequest(@PathVariable Long id) {
        List<String> response = service.sendRequest(id, "get_time");

        SensorModel sensor = new SensorModel(response.get(0), response.get(1));
        return ResponseEntity.ok(sensor);
    }

    @GetMapping("/{id}")
    public ResponseEntity<SensorModel> getSensorResponse(@PathVariable Long id) {
        List<String> response = service.getSensorResponse(id);

        SensorModel sensor = new SensorModel(response.get(0), response.get(1));
        return ResponseEntity.ok(sensor);
    }
}
