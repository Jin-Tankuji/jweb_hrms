package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.Leave;
import org.springframework.data.jpa.repository.JpaRepository;

public interface LeaveRepository extends JpaRepository<Leave, String> {
}
