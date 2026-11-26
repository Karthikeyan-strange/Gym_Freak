package com.gym.service;

import com.gym.dto.MemberRequestDTO;
import com.gym.dto.MemberResponseDTO;

import java.util.List;

public interface MemberService {

    MemberResponseDTO addMember(MemberRequestDTO requestDTO);

    List<MemberResponseDTO> getAllMembers();

    MemberResponseDTO getMemberById(Long id);

    MemberResponseDTO updateMember(Long id, MemberRequestDTO requestDTO);

    void deleteMember(Long id);

    List<MemberResponseDTO> searchMembers(String query);
}
