package com.gym.dto;

public class ExerciseResponseDTO {

    private Long exerciseId;
    private Long planId;
    private String planName;
    private String exerciseName;
    private String muscleGroup;
    private Integer sets;
    private Integer reps;
    private Integer restSeconds;
    private Double weight;
    private String instructions;

    public ExerciseResponseDTO() {
    }

    public ExerciseResponseDTO(Long exerciseId, Long planId, String planName, String exerciseName, String muscleGroup, Integer sets, Integer reps, Integer restSeconds, Double weight, String instructions) {
        this.exerciseId = exerciseId;
        this.planId = planId;
        this.planName = planName;
        this.exerciseName = exerciseName;
        this.muscleGroup = muscleGroup;
        this.sets = sets;
        this.reps = reps;
        this.restSeconds = restSeconds;
        this.weight = weight;
        this.instructions = instructions;
    }

    public Long getExerciseId() {
        return exerciseId;
    }

    public void setExerciseId(Long exerciseId) {
        this.exerciseId = exerciseId;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
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
