package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.DeptRequest;
import com.nhom2.hrms.entity.Department;
import com.nhom2.hrms.mapper.DeptMapper;
import com.nhom2.hrms.repository.DeptRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeptService {
    DeptRepository deptRepository;
    DeptMapper deptMapper;

    // Creates a new department on the request and saves it to the database
    public Department createDepartment(DeptRequest req) {
        Department dept = deptMapper.toDepartment(req);
        return deptRepository.save(dept);
    }

    // Retrieves all departments from the database
    public List<Department> getDepartments() {
        return deptRepository.findAll();
    }

    // Retrieves a specific department by its ID, throws an exception if not found
    public Department getDepartment(String id) {
        return deptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
    }

    // Updates an existing department on the provided request and ID
    public Department updateDepartment(DeptRequest req, String id) {
        Department dept = getDepartment(id);
        deptMapper.updateDept(req, dept);
        return deptRepository.save(dept);
    }

    // Deletes a position by its ID
    public void deleteDepartment(String id) {
        deptRepository.deleteById(id);
    }
}