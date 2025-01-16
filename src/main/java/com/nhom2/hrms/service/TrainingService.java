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
    public void createTraining(TrainingRequest req) {
        if (trainingRepository.existsByCourseName((req.getCourseName()))) {
            throw new RuntimeException("Chương trình đào tạo đã tồn tại!");
        }

        Training training = trainingMapper.toTraining(req);
        trainingRepository.save(training);
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
    public void updateTraining( String id, TrainingRequest req) {
        Training exTraining = trainingRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy khóa đào tạo"));
        trainingMapper.updateTraining(req, exTraining);

        trainingRepository.save(exTraining);
    }

    // Deletes a Training by its ID
    public void deleteTraining(String id) {
        if (!trainingRepository.existsById(id)) {
            throw new RuntimeException("Không tìm thấy chương trình đào tạo cần xóa");
        }
        trainingRepository.deleteById(id);
    }

    public List<Training> searchTrainingByCourseName(String courseName) {
        return trainingRepository.findTrainingByCourseNameContainingIgnoreCase(courseName);
    }
}
