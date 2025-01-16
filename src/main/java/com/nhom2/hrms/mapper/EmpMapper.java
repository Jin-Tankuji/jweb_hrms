package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.EmpRequest;
import com.nhom2.hrms.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmpMapper {

    // Bỏ qua ánh xạ trực tiếp của department và position
    @Mapping(target = "department", ignore = true)
    @Mapping(target = "position", ignore = true)
    Employee toEmployee(EmpRequest req);

    @Mapping(target = "department", ignore = true)
    @Mapping(target = "position", ignore = true)
    void updateEmployee(EmpRequest req, @MappingTarget Employee emp);

    EmpRequest toEmpRequest(Employee emp);
}

