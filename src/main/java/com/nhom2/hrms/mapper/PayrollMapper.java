package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.PayrollRequest;
import com.nhom2.hrms.entity.Payroll;
import org.mapstruct.Mapper;
import org.mapstruct.MappingTarget;

@Mapper(componentModel = "spring")
public interface PayrollMapper {
    Payroll toPayroll(PayrollRequest req);
    void updatePayroll(PayrollRequest req, @MappingTarget Payroll payroll);
}
