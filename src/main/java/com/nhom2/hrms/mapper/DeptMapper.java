package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.DeptRequest;
import com.nhom2.hrms.entity.Department;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface DeptMapper {
    Department toDepartment(DeptRequest req);
    void updateDept(DeptRequest req, @MappingTarget Department dept);
}
