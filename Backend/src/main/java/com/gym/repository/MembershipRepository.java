package com.gym.repository;

import com.gym.entity.Membership;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface MembershipRepository extends JpaRepository<Membership, Long> {

    List<Membership> findByMemberMemberIdOrderByCreatedAtDesc(Long memberId);

    List<Membership> findByMembershipStatus(String membershipStatus);

    long countByMembershipStatus(String membershipStatus);

    @Query("SELECT m FROM Membership m WHERE m.member.memberId = :memberId AND m.membershipStatus = 'ACTIVE'")
    List<Membership> findActiveMembershipsByMemberId(@Param("memberId") Long memberId);

    @Query("SELECT m FROM Membership m WHERE m.membershipStatus = 'ACTIVE' AND m.endDate < :today")
    List<Membership> findDueForExpiration(@Param("today") LocalDate today);

    @Query("SELECT m FROM Membership m WHERE m.membershipStatus = 'ACTIVE' AND m.endDate BETWEEN :startDate AND :endDate ORDER BY m.endDate ASC")
    List<Membership> findExpiringBetween(@Param("startDate") LocalDate startDate, @Param("endDate") LocalDate endDate);
}
