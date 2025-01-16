package com.nhom2.hrms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.sql.Date;
import java.sql.Time;
import java.time.LocalTime;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Attendance {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "attendance_id")
    String attendanceId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    Date date;

    @Column(name = "check_in")
    LocalTime checkIn;

    @Column(name = "check_out")
    LocalTime checkOut;

    @Column(name = "hours_worked", precision = 5, scale = 2)
    BigDecimal hoursWorked;

    @Column(name = "overtime_hours", precision = 5, scale = 2)
    BigDecimal overtimeHours;

    String status;
}
