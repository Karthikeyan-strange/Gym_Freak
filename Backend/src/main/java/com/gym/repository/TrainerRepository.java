package com.gym.repository;

import com.gym.entity.Trainer;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface TrainerRepository extends JpaRepository<Trainer, Long> {

    Optional<Trainer> findByEmail(String email);

    boolean existsByEmail(String email);

    boolean existsByEmailAndTrainerIdNot(String email, Long trainerId);

    List<Trainer> findBySpecializationContainingIgnoreCase(String specialization);

    @Query("SELECT t FROM Trainer t WHERE " +
           "LOWER(t.name) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(t.email) LIKE LOWER(CONCAT('%', :query, '%')) OR " +
           "LOWER(t.specialization) LIKE LOWER(CONCAT('%', :query, '%'))")
    List<Trainer> searchTrainers(@Param("query") String query);
}
