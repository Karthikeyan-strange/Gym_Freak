package com.gym.dto;

import java.util.ArrayList;
import java.util.List;

public class DashboardStatsDTO {

    private long totalMembers;
    private long totalTrainers;
    private long activeMemberships;
    private long expiredMemberships;
    private long activeWorkoutPlans;
    private List<MemberResponseDTO> recentRegistrations = new ArrayList<>();
    private List<ExpiryNotificationDTO> expiryNotifications = new ArrayList<>();

    public DashboardStatsDTO() {
    }

    public DashboardStatsDTO(long totalMembers, long totalTrainers, long activeMemberships, long expiredMemberships, long activeWorkoutPlans) {
        this.totalMembers = totalMembers;
        this.totalTrainers = totalTrainers;
        this.activeMemberships = activeMemberships;
        this.expiredMemberships = expiredMemberships;
        this.activeWorkoutPlans = activeWorkoutPlans;
    }

    public long getTotalMembers() {
        return totalMembers;
    }

    public void setTotalMembers(long totalMembers) {
        this.totalMembers = totalMembers;
    }

    public long getTotalTrainers() {
        return totalTrainers;
    }

    public void setTotalTrainers(long totalTrainers) {
        this.totalTrainers = totalTrainers;
    }

    public long getActiveMemberships() {
        return activeMemberships;
    }

    public void setActiveMemberships(long activeMemberships) {
        this.activeMemberships = activeMemberships;
    }

    public long getExpiredMemberships() {
        return expiredMemberships;
    }

    public void setExpiredMemberships(long expiredMemberships) {
        this.expiredMemberships = expiredMemberships;
    }

    public long getActiveWorkoutPlans() {
        return activeWorkoutPlans;
    }

    public void setActiveWorkoutPlans(long activeWorkoutPlans) {
        this.activeWorkoutPlans = activeWorkoutPlans;
    }

    public List<MemberResponseDTO> getRecentRegistrations() {
        return recentRegistrations;
    }

    public void setRecentRegistrations(List<MemberResponseDTO> recentRegistrations) {
        this.recentRegistrations = recentRegistrations;
    }

    public List<ExpiryNotificationDTO> getExpiryNotifications() {
        return expiryNotifications;
    }

    public void setExpiryNotifications(List<ExpiryNotificationDTO> expiryNotifications) {
        this.expiryNotifications = expiryNotifications;
    }
}
