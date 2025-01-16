package com.nhom2.hrms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Date;

@Data
@Entity
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class EmpPerformance {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "performance_id")
    String performanceId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @Column(name = "project_name")
    String projectName;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "end_date")
    Date endDate;

    @Column(name = "contribution")
    String contribution;

    String comments;

    @Column(name = "review_date")
    Date reviewDate;

    String reviewer;
}
