package com.nhom2.hrms.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmpPerformanceRequest {
    String employeeId;
    String projectName;
    Date startDate;
    Date endDate;
    String contribution;
    String comments;
    Date reviewDate;
    String reviewer;
}
