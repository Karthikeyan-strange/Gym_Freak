package com.gym.repository;

import com.gym.entity.WorkoutPlan;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface WorkoutPlanRepository extends JpaRepository<WorkoutPlan, Long> {

    List<WorkoutPlan> findByMemberMemberId(Long memberId);

    List<WorkoutPlan> findByTrainerTrainerId(Long trainerId);

    long countByStatus(String status);
}
