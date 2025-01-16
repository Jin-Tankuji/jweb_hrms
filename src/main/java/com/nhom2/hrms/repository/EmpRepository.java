package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.Employee;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface EmpRepository extends JpaRepository<Employee, String> {
    List<Employee> findByFirstNameContainingIgnoreCase(String firstName);
}
