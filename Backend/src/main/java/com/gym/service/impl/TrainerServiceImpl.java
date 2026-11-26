package com.gym.service.impl;

import com.gym.dto.TrainerRequestDTO;
import com.gym.dto.TrainerResponseDTO;
import com.gym.entity.Trainer;
import com.gym.exception.DuplicateResourceException;
import com.gym.exception.ResourceNotFoundException;
import com.gym.repository.TrainerRepository;
import com.gym.service.TrainerService;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

@Service
@Transactional
public class TrainerServiceImpl implements TrainerService {

    private final TrainerRepository trainerRepository;

    public TrainerServiceImpl(TrainerRepository trainerRepository) {
        this.trainerRepository = trainerRepository;
    }

    @Override
    public TrainerResponseDTO addTrainer(TrainerRequestDTO requestDTO) {
        if (trainerRepository.existsByEmail(requestDTO.getEmail())) {
            throw new DuplicateResourceException("Trainer with email " + requestDTO.getEmail() + " already exists");
        }

        Trainer trainer = new Trainer();
        trainer.setName(requestDTO.getName());
        trainer.setEmail(requestDTO.getEmail());
        trainer.setPhone(requestDTO.getPhone());
        trainer.setSpecialization(requestDTO.getSpecialization());
        trainer.setExperience(requestDTO.getExperience());
        trainer.setSalary(requestDTO.getSalary());
        trainer.setJoiningDate(requestDTO.getJoiningDate());

        Trainer savedTrainer = trainerRepository.save(trainer);
        return mapToResponseDTO(savedTrainer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainerResponseDTO> getAllTrainers() {
        return trainerRepository.findAll().stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public TrainerResponseDTO getTrainerById(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer", "id", id));
        return mapToResponseDTO(trainer);
    }

    @Override
    public TrainerResponseDTO updateTrainer(Long id, TrainerRequestDTO requestDTO) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer", "id", id));

        if (trainerRepository.existsByEmailAndTrainerIdNot(requestDTO.getEmail(), id)) {
            throw new DuplicateResourceException("Email " + requestDTO.getEmail() + " is already in use by another trainer");
        }

        trainer.setName(requestDTO.getName());
        trainer.setEmail(requestDTO.getEmail());
        trainer.setPhone(requestDTO.getPhone());
        trainer.setSpecialization(requestDTO.getSpecialization());
        trainer.setExperience(requestDTO.getExperience());
        trainer.setSalary(requestDTO.getSalary());
        if (requestDTO.getJoiningDate() != null) {
            trainer.setJoiningDate(requestDTO.getJoiningDate());
        }

        Trainer updatedTrainer = trainerRepository.save(trainer);
        return mapToResponseDTO(updatedTrainer);
    }

    @Override
    public void deleteTrainer(Long id) {
        Trainer trainer = trainerRepository.findById(id)
                .orElseThrow(() -> new ResourceNotFoundException("Trainer", "id", id));
        trainerRepository.delete(trainer);
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainerResponseDTO> searchTrainers(String query) {
        if (query == null || query.trim().isEmpty()) {
            return getAllTrainers();
        }
        return trainerRepository.searchTrainers(query.trim()).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    @Override
    @Transactional(readOnly = true)
    public List<TrainerResponseDTO> getTrainersBySpecialization(String specialization) {
        return trainerRepository.findBySpecializationContainingIgnoreCase(specialization).stream()
                .map(this::mapToResponseDTO)
                .collect(Collectors.toList());
    }

    private TrainerResponseDTO mapToResponseDTO(Trainer trainer) {
        TrainerResponseDTO dto = new TrainerResponseDTO();
        dto.setTrainerId(trainer.getTrainerId());
        dto.setName(trainer.getName());
        dto.setEmail(trainer.getEmail());
        dto.setPhone(trainer.getPhone());
        dto.setSpecialization(trainer.getSpecialization());
        dto.setExperience(trainer.getExperience());
        dto.setSalary(trainer.getSalary());
        dto.setJoiningDate(trainer.getJoiningDate());
        dto.setCreatedAt(trainer.getCreatedAt());
        dto.setActiveClientsCount(trainer.getWorkoutPlans() != null ? trainer.getWorkoutPlans().size() : 0);
        return dto;
    }
}
