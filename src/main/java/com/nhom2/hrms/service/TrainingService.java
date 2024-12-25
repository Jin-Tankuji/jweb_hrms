package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.TrainingRequest;
import com.nhom2.hrms.entity.Training;
import com.nhom2.hrms.mapper.TrainingMapper;
import com.nhom2.hrms.repository.TrainingRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainingService {
    TrainingRepository trainingRepository;
    TrainingMapper trainingMapper;

    // Creates a new Training on the request and saves it to the database
    public Training createTraining(TrainingRequest req) {
        Training training = trainingMapper.toTraining(req);
        return trainingRepository.save(training);
    }

    // Retrieves all Trainings from the database
    public List<Training> getTrainings() {
        return trainingRepository.findAll();
    }

    // Retrieves a specific Training by its ID, throws an exception if not found
    public Training getTraining(String id) {
        return trainingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin đào tào"));
    }

    // Updates an existing Training on the provided request and ID
    public Training updateTraining(TrainingRequest req, String id) {
        Training training = getTraining(id);
        trainingMapper.updateTraining(req, training);
        return trainingRepository.save(training);
    }

    // Deletes a Training by its ID
    public void deleteTraining(String id) {
        trainingRepository.deleteById(id);
    }
}
