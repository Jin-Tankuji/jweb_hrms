package com.nhom2.hrms.dto.request;

import com.nhom2.hrms.entity.Employee;
import lombok.*;
import lombok.experimental.FieldDefaults;
import org.springframework.format.annotation.DateTimeFormat;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class AttdRequest {
    String employeeId;
    Date date;
    @DateTimeFormat (pattern = "HH:mm") // Định dạng chuỗi nhập
    LocalTime checkIn;

    @DateTimeFormat(pattern = "HH:mm")
    LocalTime checkOut;

    BigDecimal hoursWorked;
    BigDecimal overtimeHours;
    String status;
}
