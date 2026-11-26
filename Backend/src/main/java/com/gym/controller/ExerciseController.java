package com.gym.controller;

import com.gym.dto.ExerciseRequestDTO;
import com.gym.dto.ExerciseResponseDTO;
import com.gym.service.ExerciseService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/exercises")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class ExerciseController {

    private final ExerciseService exerciseService;

    public ExerciseController(ExerciseService exerciseService) {
        this.exerciseService = exerciseService;
    }

    @PostMapping
    public ResponseEntity<ExerciseResponseDTO> addExercise(@Valid @RequestBody ExerciseRequestDTO requestDTO) {
        ExerciseResponseDTO created = exerciseService.addExercise(requestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<ExerciseResponseDTO>> getAllExercises() {
        return ResponseEntity.ok(exerciseService.getAllExercises());
    }

    @GetMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> getExerciseById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(exerciseService.getExerciseById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<ExerciseResponseDTO> updateExercise(
            @PathVariable("id") Long id,
            @Valid @RequestBody ExerciseRequestDTO requestDTO) {
        return ResponseEntity.ok(exerciseService.updateExercise(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteExercise(@PathVariable("id") Long id) {
        exerciseService.deleteExercise(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/plan/{planId}")
    public ResponseEntity<List<ExerciseResponseDTO>> getExercisesByPlanId(@PathVariable("planId") Long planId) {
        return ResponseEntity.ok(exerciseService.getExercisesByPlanId(planId));
    }
}
