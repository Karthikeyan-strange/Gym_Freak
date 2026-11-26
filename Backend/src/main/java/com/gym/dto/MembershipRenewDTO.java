package com.gym.dto;

import java.time.LocalDate;

public class MembershipRenewDTO {

    private String planName;
    private Double amount;
    private LocalDate startDate;
    private LocalDate endDate;
    private String paymentStatus = "PAID";

    public MembershipRenewDTO() {
    }

    public MembershipRenewDTO(String planName, Double amount, LocalDate startDate, LocalDate endDate, String paymentStatus) {
        this.planName = planName;
        this.amount = amount;
        this.startDate = startDate;
        this.endDate = endDate;
        this.paymentStatus = (paymentStatus != null) ? paymentStatus : "PAID";
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
}
