package com.gym.controller;

import com.gym.dto.TrainerRequestDTO;
import com.gym.dto.TrainerResponseDTO;
import com.gym.service.TrainerService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/trainers")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class TrainerController {

    private final TrainerService trainerService;

    public TrainerController(TrainerService trainerService) {
        this.trainerService = trainerService;
    }

    @PostMapping
    public ResponseEntity<TrainerResponseDTO> createTrainer(@Valid @RequestBody TrainerRequestDTO requestDTO) {
        TrainerResponseDTO created = trainerService.addTrainer(requestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<TrainerResponseDTO>> getAllTrainers(
            @RequestParam(value = "query", required = false) String query) {
        if (query != null && !query.trim().isEmpty()) {
            return ResponseEntity.ok(trainerService.searchTrainers(query));
        }
        return ResponseEntity.ok(trainerService.getAllTrainers());
    }

    @GetMapping("/{id}")
    public ResponseEntity<TrainerResponseDTO> getTrainerById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(trainerService.getTrainerById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<TrainerResponseDTO> updateTrainer(
            @PathVariable("id") Long id,
            @Valid @RequestBody TrainerRequestDTO requestDTO) {
        return ResponseEntity.ok(trainerService.updateTrainer(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTrainer(@PathVariable("id") Long id) {
        trainerService.deleteTrainer(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/search")
    public ResponseEntity<List<TrainerResponseDTO>> searchTrainers(@RequestParam("query") String query) {
        return ResponseEntity.ok(trainerService.searchTrainers(query));
    }

    @GetMapping("/specialization/{specialization}")
    public ResponseEntity<List<TrainerResponseDTO>> getBySpecialization(
            @PathVariable("specialization") String specialization) {
        return ResponseEntity.ok(trainerService.getTrainersBySpecialization(specialization));
    }
}
