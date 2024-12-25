package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.AttdRequest;
import com.nhom2.hrms.entity.Attendance;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface AttdMapper {
    Attendance toAttendance(AttdRequest req);
    void updateAttendance(AttdRequest req, @MappingTarget Attendance attd);
}
