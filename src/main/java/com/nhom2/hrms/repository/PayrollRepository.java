package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.Payroll;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll, String> {
}
