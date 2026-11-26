package com.gym.controller;

import com.gym.dto.MembershipRenewDTO;
import com.gym.dto.MembershipRequestDTO;
import com.gym.dto.MembershipResponseDTO;
import com.gym.service.MembershipService;
import jakarta.validation.Valid;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/memberships")
@CrossOrigin(origins = {"http://localhost:5173", "http://localhost:3000"})
public class MembershipController {

    private final MembershipService membershipService;

    public MembershipController(MembershipService membershipService) {
        this.membershipService = membershipService;
    }

    @PostMapping
    public ResponseEntity<MembershipResponseDTO> createMembership(@Valid @RequestBody MembershipRequestDTO requestDTO) {
        MembershipResponseDTO created = membershipService.createMembership(requestDTO);
        return new ResponseEntity<>(created, HttpStatus.CREATED);
    }

    @GetMapping
    public ResponseEntity<List<MembershipResponseDTO>> getAllMemberships() {
        return ResponseEntity.ok(membershipService.getAllMemberships());
    }

    @GetMapping("/active")
    public ResponseEntity<List<MembershipResponseDTO>> getActiveMemberships() {
        return ResponseEntity.ok(membershipService.getActiveMemberships());
    }

    @GetMapping("/expired")
    public ResponseEntity<List<MembershipResponseDTO>> getExpiredMemberships() {
        return ResponseEntity.ok(membershipService.getExpiredMemberships());
    }

    @GetMapping("/{id}")
    public ResponseEntity<MembershipResponseDTO> getMembershipById(@PathVariable("id") Long id) {
        return ResponseEntity.ok(membershipService.getMembershipById(id));
    }

    @PutMapping("/{id}")
    public ResponseEntity<MembershipResponseDTO> updateMembership(
            @PathVariable("id") Long id,
            @Valid @RequestBody MembershipRequestDTO requestDTO) {
        return ResponseEntity.ok(membershipService.updateMembership(id, requestDTO));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteMembership(@PathVariable("id") Long id) {
        membershipService.deleteMembership(id);
        return ResponseEntity.noContent().build();
    }

    @PostMapping("/{id}/renew")
    public ResponseEntity<MembershipResponseDTO> renewMembership(
            @PathVariable("id") Long id,
            @RequestBody(required = false) MembershipRenewDTO renewDTO) {
        MembershipResponseDTO renewed = membershipService.renewMembership(id, renewDTO);
        return new ResponseEntity<>(renewed, HttpStatus.CREATED);
    }

    @GetMapping("/member/{memberId}")
    public ResponseEntity<List<MembershipResponseDTO>> getMembershipsByMemberId(
            @PathVariable("memberId") Long memberId) {
        return ResponseEntity.ok(membershipService.getMembershipsByMemberId(memberId));
    }
}
