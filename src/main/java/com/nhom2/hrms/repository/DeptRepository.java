package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.Department;
import org.springframework.data.jpa.repository.JpaRepository;

public interface DeptRepository extends JpaRepository<Department, String> {
}
