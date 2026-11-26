package com.gym.service.impl;

import com.gym.dto.MemberRequestDTO;
import com.gym.dto.MemberResponseDTO;
import com.gym.entity.Member;
import com.gym.entity.Membership;
import com.gym.exception.DuplicateResourceException;
import com.gym.exception.ResourceNotFoundException;
import com.gym.repository.MemberRepository;
import com.gym.repository.MembershipRepository;
import com.gym.service.MemberService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class MemberServiceImpl implements MemberService {

    private final MemberRepository memberRepository;
    private final MembershipRepository membershipRepository;

    public MemberServiceImpl(MemberRepository memberRepository, MembershipRepository membershipRepository) {
        this.memberRepository = memberRepository;
        this.membershipRepository = membershipRepository;
    }

    @Override
    public MemberResponseDTO addMember(MemberRequestDTO requestDTO) {
        if (memberRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Member with email " + requestDTO.getEmail() + " already exists");
        }

        Member member = new Member();
        member.setName(requestDTO.getName());
        member.setEmail(requestDTO.getEmail());
        member.setPhone(requestDTO.getPhone());
        member.setGender(requestDTO.getGender());
        member.setAge(requestDTO.getAge());
        member.setHeight(requestDTO.getHeight());
        member.setWeight(requestDTO.getWeight());
        member.setJoinDate(requestDTO.getJoinDate());

        Member savedMember = memberRepository.save(member);
        return mapToResponseDTO(savedMember);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberResponseDTO> getAllMembers() {
        return memberRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public MemberResponseDTO getMemberById(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
        return mapToResponseDTO(member);
    }

    @Override
    public MemberResponseDTO updateMember(Long id, MemberRequestDTO requestDTO) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));

        if (memberRepository.existsByEmailAndMemberIdNot(requestDTO.getEmail(), id)) {
            throw new DuplicateResourceException("Email " + requestDTO.getEmail() + " is already in use by another member");
        }

        member.setName(requestDTO.getName());
        member.setEmail(requestDTO.getEmail());
        member.setPhone(requestDTO.getPhone());
        member.setGender(requestDTO.getGender());
        member.setAge(requestDTO.getAge());
        member.setHeight(requestDTO.getHeight());
        member.setWeight(requestDTO.getWeight());
        if (requestDTO.getJoinDate() != null) {
            member.setJoinDate(requestDTO.getJoinDate());
        }

        Member updatedMember = memberRepository.save(member);
        return mapToResponseDTO(updatedMember);
    }

    @Override
    public void deleteMember(Long id) {
        Member member = memberRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Member", "id", id));
        memberRepository.delete(member);
    }

    @Override
    @Transactional(readOnly = true)
    public List<MemberResponseDTO> searchMembers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllMembers();
        }
        return memberRepository.searchMembers(query.trim()).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private MemberResponseDTO mapToResponseDTO(Member member) {
        MemberResponseDTO dto = new MemberResponseDTO();
        dto.setMemberId(member.getMemberId());
        dto.setName(member.getName());
        dto.setEmail(member.getEmail());
        dto.setPhone(member.getPhone());
        dto.setGender(member.getGender());
        dto.setAge(member.getAge());
        dto.setHeight(member.getHeight());
        dto.setWeight(member.getWeight());
        dto.setJoinDate(member.getJoinDate());
        dto.setCreatedAt(member.getCreatedAt());

        // Check active membership
        List<Membership> activeMemberships = membershipRepository.findActiveMembershipsByMemberId(member.getMemberId());
        if (!activeMemberships.isEmpty()) {
            Membership active = activeMemberships.get(0);
            dto.setCurrentMembershipStatus(active.getMembershipStatus());
            dto.setCurrentPlanName(active.getPlanName());
        } else {
            dto.setCurrentMembershipStatus("INACTIVE");
            dto.setCurrentPlanName("None");
        }

        return dto;
    }
}
