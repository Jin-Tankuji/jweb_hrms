package com.nhom2.hrms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.sql.Date;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Training {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "training_id")
    String trainingId;

    @Column(name = "course_name")
    String courseName;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "end_date")
    Date endDate;

    String status;
    String description;
}
