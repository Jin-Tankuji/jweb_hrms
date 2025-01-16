package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.EmpPerformance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface EmpPRepository extends JpaRepository<EmpPerformance, String> {
    boolean existsByProjectName(String name);
}