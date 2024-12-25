package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.TrainingRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.entity.Training;
import com.nhom2.hrms.service.TrainingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/trainings")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainingController {
    TrainingService trainingService;

    @PostMapping("/create")
    ApiResponse<Training> createTraining(@RequestBody TrainingRequest req) {
        return ApiResponse.<Training>builder()
                .result(trainingService.createTraining(req))
                .build();
    }

    @GetMapping("/read")
    ApiResponse<List<Training>> getTrainings() {
        return ApiResponse.<List<Training>>builder()
                .result(trainingService.getTrainings())
                .build();
    }

    @GetMapping("/read/{trainId}")
    ApiResponse<Training> getTraining(@PathVariable String trainId) {
        return ApiResponse.<Training>builder()
                .result(trainingService.getTraining(trainId))
                .build();
    }

    @PutMapping("/update/{trainId}")
    ApiResponse<Training> updateTraining(@PathVariable String trainId, @RequestBody TrainingRequest req) {
        return ApiResponse.<Training>builder()
                .result(trainingService.updateTraining(req, trainId))
                .build();
    }

    @DeleteMapping("/delete/{trainId}")
    String deleteTraining(@PathVariable String trainId) {
        trainingService.deleteTraining(trainId);
        return "Training info has been deleted";
    }
}
