package com.nhom2.hrms.repository;

import com.nhom2.hrms.entity.Position;
import org.springframework.data.jpa.repository.JpaRepository;

public interface PosRepository extends JpaRepository<Position, String> {
}
