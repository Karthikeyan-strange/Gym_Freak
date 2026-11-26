package com.gym.dto;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import java.time.LocalDate;

public class MembershipRequestDTO {

    @NotNull(message = "Member ID is required")
    private Long memberId;

    @NotBlank(message = "Plan name is required (e.g., Monthly, Quarterly, Half-Yearly, Yearly)")
    private String planName;

    @NotNull(message = "Amount is required")
    @Positive(message = "Amount must be a positive number")
    private Double amount;

    @NotNull(message = "Start date is required")
    private LocalDate startDate;

    private LocalDate endDate;

    private String paymentStatus = "PAID";

    private String membershipStatus = "ACTIVE";

    public MembershipRequestDTO() {
    }

    public MembershipRequestDTO(Long memberId, String planName, Double amount, LocalDate startDate, LocalDate endDate, String paymentStatus, String membershipStatus) {
        this.memberId = memberId;
        this.planName = planName;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentStatus = (paymentStatus != null) ? paymentStatus : "PAID";
        this.membershipStatus = (membershipStatus != null) ? membershipStatus : "ACTIVE";
    }

    public Long getMemberId() {
        return memberId;
    }

    public void setMemberId(Long memberId) {
        this.memberId = memberId;
    }

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public Double getAmount() {
        return amount;
    }

    public void setAmount(Double amount) {
        this.amount = amount;
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

    public String getPaymentStatus() {
        return paymentStatus;
    }

    public void setPaymentStatus(String paymentStatus) {
        this.paymentStatus = paymentStatus;
    }

    public String getMembershipStatus() {
        return membershipStatus;
    }

    public void setMembershipStatus(String membershipStatus) {
        this.membershipStatus = membershipStatus;
    }
}
