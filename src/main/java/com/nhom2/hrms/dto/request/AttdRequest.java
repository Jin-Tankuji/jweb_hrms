package com.nhom2.hrms.dto.request;

import com.nhom2.hrms.entity.Employee;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttdRequest {
    Employee employee;
    Date date;
    Time checkIn;
    Time checkOut;
    BigDecimal hoursWorked;
    BigDecimal overtimeHours;
    String status;
}
