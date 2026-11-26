package com.gym.dto;

import java.time.LocalDate;
import java.time.LocalDateTime;

public class MemberResponseDTO {

    private Long memberId;
    private String name;
    private String email;
    private String phone;
    private String gender;
    private Integer age;
    private Double height;
    private Double weight;
    private LocalDate joinDate;
    private LocalDateTime createdAt;
    private String currentMembershipStatus;
    private String currentPlanName;

    public MemberResponseDTO() {
    }

    public MemberResponseDTO(Long memberId, String name, String email, String phone, String gender, Integer age, Double height, Double weight, LocalDate joinDate, LocalDateTime createdAt) {
        this.memberId = memberId;
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.gender = gender;
        this.age = age;
        this.height = height;
        this.weight = weight;
        this.joinDate = joinDate;
        this.createdAt = createdAt;
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
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

    public String getGender() {
        return gender;
    }

    public void setGender(String gender) {
        this.gender = gender;
    }

    public Integer getAge() {
        return age;
    }

    public void setAge(Integer age) {
        this.age = age;
    }

    public Double getHeight() {
        return height;
    }

    public void setHeight(Double height) {
        this.height = height;
    }

    public Double getWeight() {
        return weight;
    }

    public void setWeight(Double weight) {
        this.weight = weight;
    }

    public LocalDate getJoinDate() {
        return joinDate;
    }

    public void setJoinDate(LocalDate joinDate) {
        this.joinDate = joinDate;
    }

    public LocalDateTime getCreatedAt() {
        return createdAt;
    }

    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }

    public String getCurrentMembershipStatus() {
        return currentMembershipStatus;
    }

    public void setCurrentMembershipStatus(String currentMembershipStatus) {
        this.currentMembershipStatus = currentMembershipStatus;
    }

    public String getCurrentPlanName() {
        return currentPlanName;
    }

    public void setCurrentPlanName(String currentPlanName) {
        this.currentPlanName = currentPlanName;
    }
}
