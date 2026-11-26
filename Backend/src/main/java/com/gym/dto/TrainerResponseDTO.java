package com.gym.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class TrainerResponseDTO {

    private Long trainerId;
    private String name;
    private String email;
    private String phone;
    private String specialization;
    private Integer experience;
    private Double salary;
    private LocalDate joiningDate;
    private LocalDateTime createdAt;
    private Integer activeClientsCount;

    public TrainerResponseDTO() {
    }

    public TrainerResponseDTO(Long trainerId, String name, String email, String phone, String specialization, Integer experience, Double salary, LocalDate joiningDate, LocalDateTime createdAt) {
        this.trainerId = trainerId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.specialization = specialization;
        this.experience = experience;
        this.salary = salary;
        this.joiningDate = joiningDate;
        this.createdAt = createdAt;
    }

    public Long getTrainerId() {
        return trainerId;
    }

    public void setTrainerId(Long trainerId) {
        this.trainerId = trainerId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getSpecialization() {
        return specialization;
    }

    public void setSpecialization(String specialization) {
        this.specialization = specialization;
    }

    public Integer getExperience() {
        return experience;
    }

    public void setExperience(Integer experience) {
        this.experience = experience;
    }

    public Double getSalary() {
        return salary;
    }

    public void setSalary(Double salary) {
        this.salary = salary;
    }

    public LocalDate getJoiningDate() {
        return joiningDate;
    }

    public void setJoiningDate(LocalDate joiningDate) {
        this.joiningDate = joiningDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public Integer getActiveClientsCount() {
        return activeClientsCount;
    }

    public void setActiveClientsCount(Integer activeClientsCount) {
        this.activeClientsCount = activeClientsCount;
    }
}
