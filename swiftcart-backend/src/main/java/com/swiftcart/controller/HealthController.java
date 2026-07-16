package com.swiftcart.controller;

import com.swiftcart.dto.ApiResponse;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;

@RestController
public class HealthController {

    @GetMapping("/health")
    public ResponseEntity<Map<String, Object>> health() {
        Map<String, Object> response = new HashMap<>();
        response.put("status", "API is running");
        response.put("timestamp", LocalDateTime.now());
        response.put("application", "SwiftCart Backend");
        return ResponseEntity.ok(response);
    }
}


