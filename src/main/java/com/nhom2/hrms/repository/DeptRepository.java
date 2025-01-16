package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface DeptRepository extends JpaRepository<Department, String> {
    List<Department> findByNameContainingIgnoreCase(String name);
}
