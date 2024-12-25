package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.LeaveRequest;
import com.nhom2.hrms.entity.Leave;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface LeaveMapper {
    Leave toLeave(LeaveRequest req);
    void updateLeave(LeaveRequest req, @MappingTarget Leave leave);
}
