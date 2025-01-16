package com.nhom2.hrms.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class PayrollRequest {
    String employeeId;
    String payPeriod;
    BigDecimal basicSalary;
    BigDecimal overtimePay;
    BigDecimal deductions;
    BigDecimal totalSalary;
    int paymentDate;
}
