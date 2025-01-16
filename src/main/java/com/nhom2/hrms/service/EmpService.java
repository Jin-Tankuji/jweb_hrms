package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.EmpRequest;
import com.nhom2.hrms.entity.Department;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.entity.Position;
import com.nhom2.hrms.mapper.EmpMapper;
import com.nhom2.hrms.repository.DeptRepository;
import com.nhom2.hrms.repository.EmpRepository;
import com.nhom2.hrms.repository.PosRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmpService {
    EmpRepository empRepository;
    DeptRepository deptRepository;
    EmpMapper empMapper;
    PosRepository posRepository;

    public List<String> getAllDepartmentId() {
        return deptRepository.findAll().stream()
                .map(Department::getDepartmentId)
                .collect(Collectors.toList());
    }

    public List<String> getAllPositionId() {
        return posRepository.findAll().stream()
                .map(Position::getPositionId)
                .collect(Collectors.toList());
    }

    // Creates a new employee on the request and saves it to the database
    public Employee createEmployee(EmpRequest req) {
        Employee emp = empMapper.toEmployee(req);

        // Lấy Department từ database
        Department dept = deptRepository.findById(req.getDepartmentId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy phòng ban"));

        // Lấy Position từ database
        Position pos = posRepository.findById(req.getPositionId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vị trí"));

        emp.setDepartment(dept);
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
    public Employee updateEmployee(String id, EmpRequest req) {
        Employee existingEmployee = empRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        empMapper.updateEmployee(req, existingEmployee);
        return empRepository.save(existingEmployee);
    }

    // Deletes a employee by its ID
    public void deleteEmployee(String id) {
        empRepository.deleteById(id);
    }

    public List<Employee> searchEmployee(String firstName) {
        return empRepository.findByFirstNameContainingIgnoreCase(firstName);
    }


}
