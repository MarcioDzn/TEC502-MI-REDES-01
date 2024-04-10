package com.pbl.broker.Broker.controllers;

import com.pbl.broker.Broker.models.RequestModel;
import com.pbl.broker.Broker.models.ResponseModel;
import com.pbl.broker.Broker.services.BrokerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/sensor")
public class BrokerController {
    BrokerService service = new BrokerService();

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> getSensorContinuousResponse() {
        ResponseModel response = service.getSensorContinuousResponse();

        return ResponseEntity.ok(response);
    }

    @PostMapping("/{id}")
    public ResponseEntity sendSensorReq(@RequestBody RequestModel request) {
        service.sendSensorReq(request.getCommand());

        return ResponseEntity.ok().build();
    }
}
