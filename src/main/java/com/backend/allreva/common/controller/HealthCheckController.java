package com.backend.allreva.common.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.env.Environment;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;

@Controller
public class HealthCheckController {
    @Autowired
    private Environment environment;

    @RequestMapping("/health")
    public ResponseEntity<String> healthCheck() {
        return ResponseEntity.ok("alive");
    }

    @RequestMapping("/color")
    public ResponseEntity<String> colorCheck() {
        if (environment.containsProperty("color")) {
            String customEnv = environment.getProperty("color");
            return ResponseEntity.ok(customEnv);
        } else return ResponseEntity.ok("NoCOLOR");
    }
}
