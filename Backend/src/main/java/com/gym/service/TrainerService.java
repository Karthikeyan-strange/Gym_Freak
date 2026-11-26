package com.gym.service;

import com.gym.dto.TrainerRequestDTO;
import com.gym.dto.TrainerResponseDTO;

import java.util.List;

public interface TrainerService {

    TrainerResponseDTO addTrainer(TrainerRequestDTO requestDTO);

    List<TrainerResponseDTO> getAllTrainers();

    TrainerResponseDTO getTrainerById(Long id);

    TrainerResponseDTO updateTrainer(Long id, TrainerRequestDTO requestDTO);

    void deleteTrainer(Long id);

    List<TrainerResponseDTO> searchTrainers(String query);

    List<TrainerResponseDTO> getTrainersBySpecialization(String specialization);
}
