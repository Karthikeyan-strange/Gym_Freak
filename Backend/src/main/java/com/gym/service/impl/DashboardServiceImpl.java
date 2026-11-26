package com.gym.service.impl;

import com.gym.dto.DashboardStatsDTO;
import com.gym.dto.ExpiryNotificationDTO;
import com.gym.dto.MemberResponseDTO;
import com.gym.entity.Member;
import com.gym.entity.Membership;
import com.gym.repository.MemberRepository;
import com.gym.repository.MembershipRepository;
import com.gym.repository.TrainerRepository;
import com.gym.repository.WorkoutPlanRepository;
import com.gym.service.DashboardService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional(readOnly = true)
public class DashboardServiceImpl implements DashboardService {

    private final MemberRepository memberRepository;
    private final TrainerRepository trainerRepository;
    private final MembershipRepository membershipRepository;
    private final WorkoutPlanRepository workoutPlanRepository;

    public DashboardServiceImpl(MemberRepository memberRepository, TrainerRepository trainerRepository, MembershipRepository membershipRepository, WorkoutPlanRepository workoutPlanRepository) {
        this.memberRepository = memberRepository;
        this.trainerRepository = trainerRepository;
        this.membershipRepository = membershipRepository;
        this.workoutPlanRepository = workoutPlanRepository;
    }

    @Override
    public DashboardStatsDTO getDashboardStats() {
        long totalMembers = memberRepository.count();
        long totalTrainers = trainerRepository.count();
        long activeMemberships = membershipRepository.countByMembershipStatus("ACTIVE");
        long expiredMemberships = membershipRepository.countByMembershipStatus("EXPIRED");
        long activeWorkoutPlans = workoutPlanRepository.countByStatus("ACTIVE");

        DashboardStatsDTO stats = new DashboardStatsDTO(
                totalMembers,
                totalTrainers,
                activeMemberships,
                expiredMemberships,
                activeWorkoutPlans
        );

        // Recent registrations (5 latest members)
        List<Member> recentMembers = memberRepository.findTop5ByOrderByCreatedAtDesc();
        List<MemberResponseDTO> recentDTOs = recentMembers.stream().map(m -> {
            MemberResponseDTO dto = new MemberResponseDTO();
            dto.setMemberId(m.getMemberId());
            dto.setName(m.getName());
            dto.setEmail(m.getEmail());
            dto.setPhone(m.getPhone());
            dto.setGender(m.getGender());
            dto.setJoinDate(m.getJoinDate());
            dto.setCreatedAt(m.getCreatedAt());
            return dto;
        }).collect(Collectors.toList());
        stats.setRecentRegistrations(recentDTOs);

        // Expiry notifications: memberships expiring within the next 7 days
        LocalDate today = LocalDate.now();
        LocalDate sevenDaysLater = today.plusDays(7);
        List<Membership> expiringSoon = membershipRepository.findExpiringBetween(today, sevenDaysLater);

        List<ExpiryNotificationDTO> notificationDTOs = expiringSoon.stream().map(mem -> {
            long daysRemaining = ChronoUnit.DAYS.between(today, mem.getEndDate());
            return new ExpiryNotificationDTO(
                    mem.getMembershipId(),
                    mem.getMember().getMemberId(),
                    mem.getMember().getName(),
                    mem.getPlanName(),
                    mem.getEndDate(),
                    daysRemaining
            );
        }).collect(Collectors.toList());
        stats.setExpiryNotifications(notificationDTOs);

        return stats;
    }
}
