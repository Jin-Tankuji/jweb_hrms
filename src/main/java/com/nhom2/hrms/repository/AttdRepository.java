package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.Attendance;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface AttdRepository extends JpaRepository<Attendance, String> {
    List<Attendance> findByEmployeeEmployeeId(String employeeId);
}