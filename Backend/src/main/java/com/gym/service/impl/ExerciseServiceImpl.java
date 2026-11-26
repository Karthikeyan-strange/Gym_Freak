package com.gym.service.impl;

import com.gym.dto.ExerciseRequestDTO;
import com.gym.dto.ExerciseResponseDTO;
import com.gym.entity.Exercise;
import com.gym.entity.WorkoutPlan;
import com.gym.exception.ResourceNotFoundException;
import com.gym.repository.ExerciseRepository;
import com.gym.repository.WorkoutPlanRepository;
import com.gym.service.ExerciseService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class ExerciseServiceImpl implements ExerciseService {

    private final ExerciseRepository exerciseRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    public ExerciseServiceImpl(ExerciseRepository exerciseRepository, WorkoutPlanRepository workoutPlanRepository) {
        this.exerciseRepository = exerciseRepository;
        this.workoutPlanRepository = workoutPlanRepository;
    }

    @Override
    public ExerciseResponseDTO addExercise(ExerciseRequestDTO requestDTO) {
        WorkoutPlan plan = workoutPlanRepository.findById(requestDTO.getPlanId())
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "id", requestDTO.getPlanId()));

        Exercise exercise = new Exercise();
        exercise.setWorkoutPlan(plan);
        exercise.setExerciseName(requestDTO.getExerciseName());
        exercise.setMuscleGroup(requestDTO.getMuscleGroup());
        exercise.setSets(requestDTO.getSets());
        exercise.setReps(requestDTO.getReps());
        exercise.setRestSeconds(requestDTO.getRestSeconds());
        exercise.setWeight(requestDTO.getWeight());
        exercise.setInstructions(requestDTO.getInstructions());

        Exercise saved = exerciseRepository.save(exercise);
        return mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getAllExercises() {
        return exerciseRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public ExerciseResponseDTO getExerciseById(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "id", id));
        return mapToResponseDTO(exercise);
    }

    @Override
    public ExerciseResponseDTO updateExercise(Long id, ExerciseRequestDTO requestDTO) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "id", id));

        if (!exercise.getWorkoutPlan().getPlanId().equals(requestDTO.getPlanId())) {
            WorkoutPlan plan = workoutPlanRepository.findById(requestDTO.getPlanId())
                    .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "id", requestDTO.getPlanId()));
            exercise.setWorkoutPlan(plan);
        }

        exercise.setExerciseName(requestDTO.getExerciseName());
        exercise.setMuscleGroup(requestDTO.getMuscleGroup());
        exercise.setSets(requestDTO.getSets());
        exercise.setReps(requestDTO.getReps());
        exercise.setRestSeconds(requestDTO.getRestSeconds());
        exercise.setWeight(requestDTO.getWeight());
        exercise.setInstructions(requestDTO.getInstructions());

        Exercise updated = exerciseRepository.save(exercise);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteExercise(Long id) {
        Exercise exercise = exerciseRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Exercise", "id", id));
        exerciseRepository.delete(exercise);
    }

    @Override
    @Transactional(readOnly = true)
    public List<ExerciseResponseDTO> getExercisesByPlanId(Long planId) {
        if (!workoutPlanRepository.existsById(planId)) {
            throw new ResourceNotFoundException("WorkoutPlan", "id", planId);
        }
        return exerciseRepository.findByWorkoutPlanPlanId(planId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private ExerciseResponseDTO mapToResponseDTO(Exercise exercise) {
        ExerciseResponseDTO dto = new ExerciseResponseDTO();
        dto.setExerciseId(exercise.getExerciseId());
        dto.setPlanId(exercise.getWorkoutPlan().getPlanId());
        dto.setPlanName(exercise.getWorkoutPlan().getPlanName());
        dto.setExerciseName(exercise.getExerciseName());
        dto.setMuscleGroup(exercise.getMuscleGroup());
        dto.setSets(exercise.getSets());
        dto.setReps(exercise.getReps());
        dto.setRestSeconds(exercise.getRestSeconds());
        dto.setWeight(exercise.getWeight());
        dto.setInstructions(exercise.getInstructions());
        return dto;
    }
}
