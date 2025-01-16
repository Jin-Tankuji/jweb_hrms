package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.EmpRequest;
import com.nhom2.hrms.dto.request.TrainingRequest;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.entity.Training;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface TrainingMapper {
    Training toTraining(TrainingRequest req);
    void updateTraining(TrainingRequest req, @MappingTarget Training training);
    TrainingRequest toTrainRequest(Training training);
}