package com.gym.repository;

import com.gym.entity.Exercise;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ExerciseRepository extends JpaRepository<Exercise, Long> {

    List<Exercise> findByWorkoutPlanPlanId(Long planId);

    void deleteByWorkoutPlanPlanId(Long planId);
}
