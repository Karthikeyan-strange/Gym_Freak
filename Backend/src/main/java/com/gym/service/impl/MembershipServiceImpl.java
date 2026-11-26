package com.gym.service.impl;

import com.gym.dto.MembershipRenewDTO;
import com.gym.dto.MembershipRequestDTO;
import com.gym.dto.MembershipResponseDTO;
import com.gym.entity.Member;
import com.gym.entity.Membership;
import com.gym.exception.BadRequestException;
import com.gym.exception.ResourceNotFoundException;
import com.gym.repository.MemberRepository;
import com.gym.repository.MembershipRepository;
import com.gym.service.MembershipService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MembershipServiceImpl implements MembershipService {

    private final MembershipRepository membershipRepository;
    private final MemberRepository memberRepository;

    public MembershipServiceImpl(MembershipRepository membershipRepository, MemberRepository memberRepository) {
        this.membershipRepository = membershipRepository;
        this.memberRepository = memberRepository;
    }

    @Override
    public MembershipResponseDTO createMembership(MembershipRequestDTO requestDTO) {
        Member member = memberRepository.findById(requestDTO.getMemberId())
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", requestDTO.getMemberId()));

        LocalDate startDate = requestDTO.getStartDate() != null ? requestDTO.getStartDate() : LocalDate.now();
        LocalDate endDate = requestDTO.getEndDate();
        if (endDate == null) {
            endDate = calculateEndDate(startDate, requestDTO.getPlanName());
        }

        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("End date cannot be before start date");
        }

        String paymentStatus = (requestDTO.getPaymentStatus() != null) ? requestDTO.getPaymentStatus().toUpperCase() : "PAID";
        String membershipStatus = (requestDTO.getMembershipStatus() != null) ? requestDTO.getMembershipStatus().toUpperCase() : "ACTIVE";

        // Business rule: If today is after endDate, status must be EXPIRED
        if (LocalDate.now().isAfter(endDate)) {
            membershipStatus = "EXPIRED";
        }

        // Business rule: A member should have only one active membership at a time.
        // If creating a new ACTIVE membership, expire any existing active memberships
        if ("ACTIVE".equalsIgnoreCase(membershipStatus)) {
            deactivateExistingActiveMemberships(member.getMemberId());
        }

        Membership membership = new Membership();
        membership.setMember(member);
        membership.setPlanName(requestDTO.getPlanName());
        membership.setAmount(requestDTO.getAmount());
        membership.setStartDate(startDate);
        membership.setEndDate(endDate);
        membership.setPaymentStatus(paymentStatus);
        membership.setMembershipStatus(membershipStatus);

        Membership saved = membershipRepository.save(membership);
        return mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipResponseDTO> getAllMemberships() {
        return membershipRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MembershipResponseDTO getMembershipById(Long id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership", "id", id));
        return mapToResponseDTO(membership);
    }

    @Override
    public MembershipResponseDTO updateMembership(Long id, MembershipRequestDTO requestDTO) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership", "id", id));

        LocalDate startDate = requestDTO.getStartDate() != null ? requestDTO.getStartDate() : membership.getStartDate();
        LocalDate endDate = requestDTO.getEndDate() != null ? requestDTO.getEndDate() : calculateEndDate(startDate, requestDTO.getPlanName());

        if (endDate.isBefore(startDate)) {
            throw new BadRequestException("End date cannot be before start date");
        }

        membership.setPlanName(requestDTO.getPlanName());
        membership.setAmount(requestDTO.getAmount());
        membership.setStartDate(startDate);
        membership.setEndDate(endDate);

        if (requestDTO.getPaymentStatus() != null) {
            membership.setPaymentStatus(requestDTO.getPaymentStatus().toUpperCase());
        }

        if (requestDTO.getMembershipStatus() != null) {
            membership.setMembershipStatus(requestDTO.getMembershipStatus().toUpperCase());
        }

        // Check if date has expired
        if (LocalDate.now().isAfter(endDate) && "ACTIVE".equalsIgnoreCase(membership.getMembershipStatus())) {
            membership.setMembershipStatus("EXPIRED");
        }

        Membership updated = membershipRepository.save(membership);
        return mapToResponseDTO(updated);
    }

    @Override
    public void deleteMembership(Long id) {
        Membership membership = membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership", "id", id));
        membershipRepository.delete(membership);
    }

    @Override
    public MembershipResponseDTO renewMembership(Long id, MembershipRenewDTO renewDTO) {
        Membership existingMembership = membershipRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Membership", "id", id));

        Member member = existingMembership.getMember();

        // Expire the previous membership
        existingMembership.setMembershipStatus("EXPIRED");
        membershipRepository.save(existingMembership);

        // Deactivate any other active membership for this member
        deactivateExistingActiveMemberships(member.getMemberId());

        // Determine plan name, amount, start date, and end date for the new renewal record
        String newPlanName = (renewDTO != null && renewDTO.getPlanName() != null && !renewDTO.getPlanName().trim().isEmpty())
                ? renewDTO.getPlanName()
                : existingMembership.getPlanName();

        Double newAmount = (renewDTO != null && renewDTO.getAmount() != null)
                ? renewDTO.getAmount()
                : existingMembership.getAmount();

        LocalDate newStartDate = (renewDTO != null && renewDTO.getStartDate() != null)
                ? renewDTO.getStartDate()
                : (existingMembership.getEndDate().isAfter(LocalDate.now()) ? existingMembership.getEndDate().plusDays(1) : LocalDate.now());

        LocalDate newEndDate = (renewDTO != null && renewDTO.getEndDate() != null)
                ? renewDTO.getEndDate()
                : calculateEndDate(newStartDate, newPlanName);

        String paymentStatus = (renewDTO != null && renewDTO.getPaymentStatus() != null)
                ? renewDTO.getPaymentStatus().toUpperCase()
                : "PAID";

        String membershipStatus = LocalDate.now().isAfter(newEndDate) ? "EXPIRED" : "ACTIVE";

        // Business rule: "Every renewal should create a new membership record instead of overwriting the previous membership history"
        Membership renewedMembership = new Membership();
        renewedMembership.setMember(member);
        renewedMembership.setPlanName(newPlanName);
        renewedMembership.setAmount(newAmount);
        renewedMembership.setStartDate(newStartDate);
        renewedMembership.setEndDate(newEndDate);
        renewedMembership.setPaymentStatus(paymentStatus);
        renewedMembership.setMembershipStatus(membershipStatus);

        Membership saved = membershipRepository.save(renewedMembership);
        return mapToResponseDTO(saved);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipResponseDTO> getActiveMemberships() {
        return membershipRepository.findByMembershipStatus("ACTIVE").stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipResponseDTO> getExpiredMemberships() {
        return membershipRepository.findByMembershipStatus("EXPIRED").stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<MembershipResponseDTO> getMembershipsByMemberId(Long memberId) {
        return membershipRepository.findByMemberMemberIdOrderByCreatedAtDesc(memberId).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private void deactivateExistingActiveMemberships(Long memberId) {
        List<Membership> activeList = membershipRepository.findActiveMembershipsByMemberId(memberId);
        for (Membership m : activeList) {
            m.setMembershipStatus("EXPIRED");
            membershipRepository.save(m);
        }
    }

    private LocalDate calculateEndDate(LocalDate startDate, String planName) {
        if (planName == null) {
            return startDate.plusMonths(1);
        }
        String plan = planName.trim().toLowerCase();
        if (plan.contains("quarter")) {
            return startDate.plusMonths(3);
        } else if (plan.contains("half") || plan.contains("6")) {
            return startDate.plusMonths(6);
        } else if (plan.contains("year") || plan.contains("annual") || plan.contains("12")) {
            return startDate.plusYears(1);
        } else {
            // Default to monthly (1 month)
            return startDate.plusMonths(1);
        }
    }

    private MembershipResponseDTO mapToResponseDTO(Membership membership) {
        MembershipResponseDTO dto = new MembershipResponseDTO();
        dto.setMembershipId(membership.getMembershipId());
        dto.setMemberId(membership.getMember().getMemberId());
        dto.setMemberName(membership.getMember().getName());
        dto.setMemberEmail(membership.getMember().getEmail());
        dto.setPlanName(membership.getPlanName());
        dto.setAmount(membership.getAmount());
        dto.setStartDate(membership.getStartDate());
        dto.setEndDate(membership.getEndDate());
        dto.setPaymentStatus(membership.getPaymentStatus());
        dto.setMembershipStatus(membership.getMembershipStatus());
        dto.setCreatedAt(membership.getCreatedAt());

        LocalDate today = LocalDate.now();
        boolean expired = today.isAfter(membership.getEndDate()) || "EXPIRED".equalsIgnoreCase(membership.getMembershipStatus());
        dto.setIsExpired(expired);

        long daysRemaining = ChronoUnit.DAYS.between(today, membership.getEndDate());
        dto.setDaysRemaining(daysRemaining >= 0 ? daysRemaining : 0);

        return dto;
    }
}
