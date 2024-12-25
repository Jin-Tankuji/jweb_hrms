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
public class Recruitment {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "recruitment_id")
    String recruitmentId;

    @ManyToOne
    @JoinColumn(name = "position_id")
    Position position;

    int quantity;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "end_date")
    Date endDate;

    String status;
    String description;
}
