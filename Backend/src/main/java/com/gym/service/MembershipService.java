package com.gym.service;

import com.gym.dto.MembershipRenewDTO;
import com.gym.dto.MembershipRequestDTO;
import com.gym.dto.MembershipResponseDTO;

import java.util.List;

public interface MembershipService {

    MembershipResponseDTO createMembership(MembershipRequestDTO requestDTO);

    List<MembershipResponseDTO> getAllMemberships();

    MembershipResponseDTO getMembershipById(Long id);

    MembershipResponseDTO updateMembership(Long id, MembershipRequestDTO requestDTO);

    void deleteMembership(Long id);

    MembershipResponseDTO renewMembership(Long id, MembershipRenewDTO renewDTO);

    List<MembershipResponseDTO> getActiveMemberships();

    List<MembershipResponseDTO> getExpiredMemberships();

    List<MembershipResponseDTO> getMembershipsByMemberId(Long memberId);
}
