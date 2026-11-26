package com.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDate;

public class WorkoutPlanRequestDTO {

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotNull(message = "Trainer ID is required")
    private Long trainerId;

    @NotBlank(message = "Plan name is required")
    private String planName;

    @NotBlank(message = "Workout goal is required (e.g. Muscle Gain, Weight Loss, Strength, Endurance, General Fitness)")
    private String goal;

    private LocalDate startDate;

    private LocalDate endDate;

    private String description;

    private String status = "ACTIVE";

    public WorkoutPlanRequestDTO() {
    }

    public WorkoutPlanRequestDTO(Long memberId, Long trainerId, String planName, String goal, LocalDate startDate, LocalDate endDate, String description, String status) {
        this.memberId = memberId;
        this.trainerId = trainerId;
        this.planName = planName;
        this.goal = goal;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.status = (status != null) ? status : "ACTIVE";
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public String getGoal() {
        return goal;
    }

    public void setGoal(String goal) {
        this.goal = goal;
    }

    public LocalDate getStartDate() {
        return startDate;
    }

    public void setStartDate(LocalDate startDate) {
        this.startDate = startDate;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }
}
