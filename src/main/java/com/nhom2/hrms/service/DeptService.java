package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.DeptRequest;
import com.nhom2.hrms.entity.Department;
import com.nhom2.hrms.mapper.DeptMapper;
import com.nhom2.hrms.repository.DeptRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeptService {

    DeptRepository deptRepository;
    DeptMapper deptMapper;

    // Tạo phòng ban mới
    public Department createDepartment(DeptRequest req) {
        Department dept = deptMapper.toDepartment(req);
        return deptRepository.save(dept);
    }

    // Lấy tất cả phòng ban
    public List<Department> getDepartments() {
        return deptRepository.findAll();
    }

    // Lấy thông tin phòng ban theo ID
    public Department getDepartment(String id) {
        return deptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));
    }

    // Cập nhật phòng ban
    public Department updateDepartment(String id, DeptRequest req) {
        Department existingDept = deptRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban để cập nhật"));
        deptMapper.updateDepartmentFromRequest(req, existingDept);
        return deptRepository.save(existingDept);
    }

    // Xóa phòng ban
    public void deleteDepartment(String id) {
        deptRepository.deleteById(id);
    }
    public List<Department> searchDepartmentsByName(String name) {
        return deptRepository.findByNameContainingIgnoreCase(name);
    }

}
