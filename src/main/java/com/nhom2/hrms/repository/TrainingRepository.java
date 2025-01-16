package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.Training;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface TrainingRepository extends JpaRepository<Training, String> {
    boolean existsByCourseName(String name);

    List<Training> findTrainingByCourseNameContainingIgnoreCase(String courseName);
}
