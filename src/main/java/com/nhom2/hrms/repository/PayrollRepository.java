package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.Attendance;
import com.nhom2.hrms.entity.Payroll;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PayrollRepository extends JpaRepository<Payroll, String> {
  List<Payroll> findByEmployeeEmployeeId(String employeeId);
}
