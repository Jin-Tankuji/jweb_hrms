package com.nhom2.hrms.entity;

import jakarta.persistence.*;
import lombok.*;
import lombok.experimental.FieldDefaults;

import java.math.BigDecimal;
import java.text.DecimalFormat;

@Entity
@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class Payroll {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    @Column(name = "payroll_id")
    String payrollId;

    @ManyToOne
    @JoinColumn(name = "employee_id")
    Employee employee;

    @Column(name = "pay_period")
    String payPeriod;

    @Column(name = "basic_salary")
    BigDecimal basicSalary;

    @Column(name = "overtime_pay")
    BigDecimal overtimePay;

    @Column(name = "deductions")
    BigDecimal deductions;

    @Column(name = "total_salary")
    BigDecimal totalSalary;

    @Column(name = "payment_date")
    int paymentDate;

    public String formatCurrency(BigDecimal amount) {
        DecimalFormat formatter = new DecimalFormat("#,###");
        return formatter.format(amount) + " VND";
    }
}
