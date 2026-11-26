package com.gym.controller;

import com.gym.dto.WorkoutPlanRequestDTO;
import com.gym.dto.WorkoutPlanResponseDTO;
import com.gym.service.WorkoutPlanService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/workout-plans")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class WorkoutPlanController {

    private final WorkoutPlanService workoutPlanService;

    public WorkoutPlanController(WorkoutPlanService workoutPlanService) {
        this.workoutPlanService = workoutPlanService;
    }

    @PostMapping
    public ResponseEntity<WorkoutPlanResponseDTO> createWorkoutPlan(
            @Valid @RequestBody WorkoutPlanRequestDTO requestDTO) {
        WorkoutPlanResponseDTO created = workoutPlanService.createWorkoutPlan(requestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<WorkoutPlanResponseDTO>> getAllWorkoutPlans() {
        return ResponseEntity.ok(workoutPlanService.getAllWorkoutPlans());
    }

    @GetMapping("/{id}")
    public ResponseEntity<WorkoutPlanResponseDTO> getWorkoutPlanById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlanById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<WorkoutPlanResponseDTO> updateWorkoutPlan(
            @PathVariable("id") Long id,
            @Valid @RequestBody WorkoutPlanRequestDTO requestDTO) {
        return ResponseEntity.ok(workoutPlanService.updateWorkoutPlan(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteWorkoutPlan(@PathVariable("id") Long id) {
        workoutPlanService.deleteWorkoutPlan(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<WorkoutPlanResponseDTO>> getWorkoutPlansByMember(
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlansByMember(memberId));
    }

    @GetMapping("/trainer/{trainerId}")
    public ResponseEntity<List<WorkoutPlanResponseDTO>> getWorkoutPlansByTrainer(
            @PathVariable("trainerId") Long trainerId) {
        return ResponseEntity.ok(workoutPlanService.getWorkoutPlansByTrainer(trainerId));
    }
}
