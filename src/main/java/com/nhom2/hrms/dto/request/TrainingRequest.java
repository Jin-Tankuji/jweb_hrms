package com.nhom2.hrms.dto.request;

import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class TrainingRequest {
    String courseName;
    Date startDate;
    Date endDate;
    String status;
    String description;
}