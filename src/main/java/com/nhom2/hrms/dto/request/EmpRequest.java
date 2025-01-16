package com.nhom2.hrms.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmpRequest {
    String employeeId;
    String firstName;
    String lastName;
    Date dateOfBirth;
    String gender;
    Date hireDate;
    String departmentId;
    String positionId;
    BigDecimal salary;
}
