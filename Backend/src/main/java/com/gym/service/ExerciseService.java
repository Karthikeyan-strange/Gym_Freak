package com.gym.service;

import com.gym.dto.ExerciseRequestDTO;
import com.gym.dto.ExerciseResponseDTO;

import java.util.List;

public interface ExerciseService {

    ExerciseResponseDTO addExercise(ExerciseRequestDTO requestDTO);

    List<ExerciseResponseDTO> getAllExercises();

    ExerciseResponseDTO getExerciseById(Long id);

    ExerciseResponseDTO updateExercise(Long id, ExerciseRequestDTO requestDTO);

    void deleteExercise(Long id);

    List<ExerciseResponseDTO> getExercisesByPlanId(Long planId);
}
