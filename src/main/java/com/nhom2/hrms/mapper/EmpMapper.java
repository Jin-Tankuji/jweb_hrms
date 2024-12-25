package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.EmpRequest;
import com.nhom2.hrms.entity.Employee;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface EmpMapper {
    Employee toEmployee(EmpRequest req);
    void updateEmployee(EmpRequest req, @MappingTarget Employee emp);
}
