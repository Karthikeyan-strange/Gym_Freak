package com.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;

public class ExerciseRequestDTO {

    @NotNull(message = "Workout plan ID is required")
    private Long planId;

    @NotBlank(message = "Exercise name is required")
    private String exerciseName;

    @NotBlank(message = "Muscle group is required (e.g. Chest, Back, Shoulders, Biceps, Triceps, Legs, Abs, Full Body)")
    private String muscleGroup;

    @Positive(message = "Sets must be a positive number")
    private Integer sets;

    @Positive(message = "Reps must be a positive number")
    private Integer reps;

    @PositiveOrZero(message = "Rest seconds must be zero or a positive number")
    private Integer restSeconds;

    @PositiveOrZero(message = "Weight must be zero or a positive number")
    private Double weight;

    private String instructions;

    public ExerciseRequestDTO() {
    }

    public ExerciseRequestDTO(Long planId, String exerciseName, String muscleGroup, Integer sets, Integer reps, Integer restSeconds, Double weight, String instructions) {
        this.planId = planId;
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
        this.weight = weight;
        this.instructions = instructions;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getExerciseName() {
        return exerciseName;
    }

    public void setExerciseName(String exerciseName) {
        this.exerciseName = exerciseName;
    }

    public String getMuscleGroup() {
        return muscleGroup;
    }

    public void setMuscleGroup(String muscleGroup) {
        this.muscleGroup = muscleGroup;
    }

    public Integer getSets() {
        return sets;
    }

    public void setSets(Integer sets) {
        this.sets = sets;
    }

    public Integer getReps() {
        return reps;
    }

    public void setReps(Integer reps) {
        this.reps = reps;
    }

    public Integer getRestSeconds() {
        return restSeconds;
    }

    public void setRestSeconds(Integer restSeconds) {
        this.restSeconds = restSeconds;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public String getInstructions() {
        return instructions;
    }

    public void setInstructions(String instructions) {
        this.instructions = instructions;
    }
}
