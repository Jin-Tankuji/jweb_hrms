package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.PosRequest;
import com.nhom2.hrms.entity.Position;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PosMapper {
    Position toPosition(PosRequest req);
    void updatePosition(PosRequest req, @MappingTarget Position pos);
}
