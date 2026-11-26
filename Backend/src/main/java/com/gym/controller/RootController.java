package com.gym.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.Map;

@RestController
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class RootController {

    @GetMapping("/")
    public ResponseEntity<Map<String, Object>> rootEndpoint() {
        Map<String, Object> response = new HashMap<>();
        response.put("application", "Gym Membership & Workout Management System API");
        response.put("status", "UP");
        response.put("version", "1.0.0");

        Map<String, String> endpoints = new HashMap<>();
        endpoints.put("dashboard", "/api/dashboard/stats");
        endpoints.put("members", "/api/members");
        endpoints.put("trainers", "/api/trainers");
        endpoints.put("memberships", "/api/memberships");
        endpoints.put("activeMemberships", "/api/memberships/active");
        endpoints.put("expiredMemberships", "/api/memberships/expired");
        endpoints.put("workoutPlans", "/api/workout-plans");
        endpoints.put("exercises", "/api/exercises");

        response.put("endpoints", endpoints);
        return ResponseEntity.ok(response);
    }
}
