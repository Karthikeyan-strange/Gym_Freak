package com.gym.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class WorkoutPlanResponseDTO {

    private Long planId;
    private Long memberId;
    private String memberName;
    private Long trainerId;
    private String trainerName;
    private String planName;
    private String goal;
    private LocalDate startDate;
    private LocalDate endDate;
    private String description;
    private String status;
    private LocalDateTime createdAt;
    private Integer exerciseCount;

    public WorkoutPlanResponseDTO() {
    }

    public WorkoutPlanResponseDTO(Long planId, Long memberId, String memberName, Long trainerId, String trainerName, String planName, String goal, LocalDate startDate, LocalDate endDate, String description, String status, LocalDateTime createdAt, Integer exerciseCount) {
        this.planId = planId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.trainerId = trainerId;
        this.trainerName = trainerName;
        this.planName = planName;
        this.goal = goal;
        this.startDate = startDate;
        this.endDate = endDate;
        this.description = description;
        this.status = status;
        this.createdAt = createdAt;
        this.exerciseCount = exerciseCount;
    }

    public Long getPlanId() {
        return planId;
    }

    public void setPlanId(Long planId) {
        this.planId = planId;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getMemberName() {
        return memberName;
    }

    public void setMemberName(String memberName) {
        this.memberName = memberName;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getTrainerName() {
        return trainerName;
    }

    public void setTrainerName(String trainerName) {
        this.trainerName = trainerName;
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

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getExerciseCount() {
        return exerciseCount;
    }

    public void setExerciseCount(Integer exerciseCount) {
        this.exerciseCount = exerciseCount;
    }
}
