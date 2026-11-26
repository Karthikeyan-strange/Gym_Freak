package com.gym.dto;

import java.time.LocalDate;

public class ExpiryNotificationDTO {

    private Long membershipId;
    private Long memberId;
    private String memberName;
    private String planName;
    private LocalDate endDate;
    private Long daysRemaining;

    public ExpiryNotificationDTO() {
    }

    public ExpiryNotificationDTO(Long membershipId, Long memberId, String memberName, String planName, LocalDate endDate, Long daysRemaining) {
        this.membershipId = membershipId;
        this.memberId = memberId;
        this.memberName = memberName;
        this.planName = planName;
        this.endDate = endDate;
        this.daysRemaining = daysRemaining;
    }

    public Long getMembershipId() {
        return membershipId;
    }

    public void setMembershipId(Long membershipId) {
        this.membershipId = membershipId;
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

    public String getPlanName() {
        return planName;
    }

    public void setPlanName(String planName) {
        this.planName = planName;
    }

    public LocalDate getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDate endDate) {
        this.endDate = endDate;
    }

    public Long getDaysRemaining() {
        return daysRemaining;
    }

    public void setDaysRemaining(Long daysRemaining) {
        this.daysRemaining = daysRemaining;
    }
}
