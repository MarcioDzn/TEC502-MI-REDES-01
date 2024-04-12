package com.pbl.broker.Broker.controllers;

import com.pbl.broker.Broker.models.RequestModel;
import com.pbl.broker.Broker.models.ResponseModel;
import com.pbl.broker.Broker.services.BrokerService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/sensor")
public class BrokerController {
    BrokerService service = new BrokerService();

    @GetMapping()
    public ResponseEntity<List<ResponseModel>> getAllSensorContinuousResponse() {
        List<ResponseModel> response = service.getAllSensorContinuousResponse();

        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ResponseModel> getSensorContinuousResponse(@PathVariable Long id) {
        ResponseModel response = service.getSensorContinuousResponse(id);

        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{id}")
    public ResponseEntity sendSensorReq(@PathVariable Long id, @RequestBody RequestModel request) {
        service.sendSensorReq(id, request.getCommand());

        return ResponseEntity.ok().build();
    }
}
