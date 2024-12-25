package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.RecmtRequest;
import com.nhom2.hrms.entity.Recruitment;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface RecmtMapper {
    Recruitment toRecruitment(RecmtRequest req);
    void updateRecruitment(RecmtRequest req, @MappingTarget Recruitment recmt);
}
