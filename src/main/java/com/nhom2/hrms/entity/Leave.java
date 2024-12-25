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
@Table(name = "leaves")
public class Leave {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "leave_id")
    String leaveId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @Column(name = "leave_type")
    String leaveType;

    @Column(name = "start_date")
    Date startDate;

    @Column(name = "end_date")
    Date endDate;

    String status;
    String reason;
}
