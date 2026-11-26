package com.gym.service.impl;

import com.gym.dto.WorkoutPlanRequestDTO;
import com.gym.dto.WorkoutPlanResponseDTO;
import com.gym.entity.Member;
import com.gym.entity.Trainer;
import com.gym.entity.WorkoutPlan;
import com.gym.exception.BadRequestException;
import com.gym.exception.ResourceNotFoundException;
import com.gym.repository.MemberRepository;
import com.gym.repository.TrainerRepository;
import com.gym.repository.WorkoutPlanRepository;
import com.gym.service.WorkoutPlanService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class WorkoutPlanServiceImpl implements WorkoutPlanService {

    private final WorkoutPlanRepository workoutPlanRepository;
    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;

    public WorkoutPlanServiceImpl(WorkoutPlanRepository workoutPlanRepository, MemberRepository memberRepository, TrainerRepository trainerRepository) {
        this.workoutPlanRepository = workoutPlanRepository;
        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
    }

    @Override
    public WorkoutPlanResponseDTO createWorkoutPlan(WorkoutPlanRequestDTO requestDTO) {
        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", requestDTO.getMemberId()));

        Trainer trainer = trainerRepository.findById(requestDTO.getTrainerId())
                .orElseThrow(() -> new ResourceNotFoundException("Trainer", "id", requestDTO.getTrainerId()));

        if (requestDTO.getStartDate() != null && requestDTO.getEndDate() != null && requestDTO.getEndDate().isBefore(requestDTO.getStartDate())) {
            throw new BadRequestException("Workout plan end date cannot be before start date");
        }

        WorkoutPlan plan = new WorkoutPlan();
        plan.setMember(member);
        plan.setTrainer(trainer);
        plan.setPlanName(requestDTO.getPlanName());
        plan.setGoal(requestDTO.getGoal());
        plan.setStartDate(requestDTO.getStartDate());
        plan.setEndDate(requestDTO.getEndDate());
        plan.setDescription(requestDTO.getDescription());
        plan.setStatus(requestDTO.getStatus() != null ? requestDTO.getStatus().toUpperCase() : "ACTIVE");

        WorkoutPlan saved = workoutPlanRepository.save(plan);
        return mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutPlanResponseDTO> getAllWorkoutPlans() {
        return workoutPlanRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public WorkoutPlanResponseDTO getWorkoutPlanById(Long id) {
        WorkoutPlan plan = workoutPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "id", id));
        return mapToResponseDTO(plan);
    }

    @Override
    public WorkoutPlanResponseDTO updateWorkoutPlan(Long id, WorkoutPlanRequestDTO requestDTO) {
        WorkoutPlan plan = workoutPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "id", id));

        if (!plan.getMember().getMemberId().equals(requestDTO.getMemberId())) {
            Member member = memberRepository.findById(requestDTO.getMemberId())
                    .orElseThrow(() -> new ResourceNotFoundException("Member", "id", requestDTO.getMemberId()));
            plan.setMember(member);
        }

        if (!plan.getTrainer().getTrainerId().equals(requestDTO.getTrainerId())) {
            Trainer trainer = trainerRepository.findById(requestDTO.getTrainerId())
                    .orElseThrow(() -> new ResourceNotFoundException("Trainer", "id", requestDTO.getTrainerId()));
            plan.setTrainer(trainer);
        }

        if (requestDTO.getStartDate() != null && requestDTO.getEndDate() != null && requestDTO.getEndDate().isBefore(requestDTO.getStartDate())) {
            throw new BadRequestException("Workout plan end date cannot be before start date");
        }

        plan.setPlanName(requestDTO.getPlanName());
        plan.setGoal(requestDTO.getGoal());
        plan.setStartDate(requestDTO.getStartDate());
        plan.setEndDate(requestDTO.getEndDate());
        plan.setDescription(requestDTO.getDescription());
        if (requestDTO.getStatus() != null) {
            plan.setStatus(requestDTO.getStatus().toUpperCase());
        }

        WorkoutPlan updated = workoutPlanRepository.save(plan);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteWorkoutPlan(Long id) {
        WorkoutPlan plan = workoutPlanRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("WorkoutPlan", "id", id));
        workoutPlanRepository.delete(plan);
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutPlanResponseDTO> getWorkoutPlansByMember(Long memberId) {
        if (!memberRepository.existsById(memberId)) {
            throw new ResourceNotFoundException("Member", "id", memberId);
        }
        return workoutPlanRepository.findByMemberMemberId(memberId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<WorkoutPlanResponseDTO> getWorkoutPlansByTrainer(Long trainerId) {
        if (!trainerRepository.existsById(trainerId)) {
            throw new ResourceNotFoundException("Trainer", "id", trainerId);
        }
        return workoutPlanRepository.findByTrainerTrainerId(trainerId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private WorkoutPlanResponseDTO mapToResponseDTO(WorkoutPlan plan) {
        WorkoutPlanResponseDTO dto = new WorkoutPlanResponseDTO();
        dto.setPlanId(plan.getPlanId());
        dto.setMemberId(plan.getMember().getMemberId());
        dto.setMemberName(plan.getMember().getName());
        dto.setTrainerId(plan.getTrainer().getTrainerId());
        dto.setTrainerName(plan.getTrainer().getName());
        dto.setPlanName(plan.getPlanName());
        dto.setGoal(plan.getGoal());
        dto.setStartDate(plan.getStartDate());
        dto.setEndDate(plan.getEndDate());
        dto.setDescription(plan.getDescription());
        dto.setStatus(plan.getStatus());
        dto.setCreatedAt(plan.getCreatedAt());
        dto.setExerciseCount(plan.getExercises() != null ? plan.getExercises().size() : 0);
        return dto;
    }
}
