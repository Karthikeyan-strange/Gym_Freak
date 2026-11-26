package com.gym.service;

import com.gym.dto.WorkoutPlanRequestDTO;
import com.gym.dto.WorkoutPlanResponseDTO;

import java.util.List;

public interface WorkoutPlanService {

    WorkoutPlanResponseDTO createWorkoutPlan(WorkoutPlanRequestDTO requestDTO);

    List<WorkoutPlanResponseDTO> getAllWorkoutPlans();

    WorkoutPlanResponseDTO getWorkoutPlanById(Long id);

    WorkoutPlanResponseDTO updateWorkoutPlan(Long id, WorkoutPlanRequestDTO requestDTO);

    void deleteWorkoutPlan(Long id);

    List<WorkoutPlanResponseDTO> getWorkoutPlansByMember(Long memberId);

    List<WorkoutPlanResponseDTO> getWorkoutPlansByTrainer(Long trainerId);
}
