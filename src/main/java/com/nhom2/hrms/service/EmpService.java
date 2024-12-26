package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.EmpRequest;
import com.nhom2.hrms.entity.Department;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.entity.Position;
import com.nhom2.hrms.mapper.EmpMapper;
import com.nhom2.hrms.repository.EmpRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmpService {
    EmpRepository empRepository;
    EmpMapper empMapper;

    // Creates a new employee on the request and saves it to the database
    public Employee createEmployee(EmpRequest req) {
        Employee emp = empMapper.toEmployee(req);

        Department dept = new Department();
        dept.setDepartmentId(req.getDepartment().getDepartmentId());
        emp.setDepartment(dept);

        Position pos = new Position();
        pos.setPositionId(req.getPosition().getPositionId());
        emp.setPosition(pos);

        return empRepository.save(emp);
    }

    // Retrieves all employees from the database
    public List<Employee> getEmployees() {
        return empRepository.findAll();
    }

    // Retrieves a specific employee by its ID, throws an exception if not found
    public Employee getEmployee(String id) {
        return empRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));
    }

    // Updates an existing employee on the provided request and ID
    public Employee updateEmployee(EmpRequest req, String id) {
        Employee emp = getEmployee(id);
        empMapper.updateEmployee(req, emp);
        return empRepository.save(emp);
    }

    // Deletes a employee by its ID
    public void deleteEmployee(String id) {
        empRepository.deleteById(id);
    }
}
