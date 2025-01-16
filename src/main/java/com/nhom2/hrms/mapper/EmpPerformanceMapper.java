package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.EmpPerformanceRequest;
import com.nhom2.hrms.dto.request.EmpRequest;
import com.nhom2.hrms.entity.EmpPerformance;
import com.nhom2.hrms.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmpPerformanceMapper {
    EmpPerformance toEmpPerformance(EmpPerformanceRequest req);
    void updateEmpPerformance(EmpPerformanceRequest req, @MappingTarget EmpPerformance empPerformance);
}
