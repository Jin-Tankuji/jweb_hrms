package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.EmpPerformanceRequest;
import com.nhom2.hrms.entity.EmpPerformance;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.mapper.EmpPerformanceMapper;
import com.nhom2.hrms.repository.EmpPRepository;
import com.nhom2.hrms.repository.EmpRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmpPerformanceService {

    EmpPRepository empPRepository;
    EmpRepository empRepository;
    EmpPerformanceMapper empPerformanceMapper;

    public List<String> getAllEmployeeId() {
        return empRepository.findAll().stream()
                .map(Employee::getEmployeeId)
                .collect(Collectors.toList());
    }

    public EmpPerformance createEmpPerformance(EmpPerformanceRequest req) {
        EmpPerformance empPerformance = empPerformanceMapper.toEmpPerformance(req);

        Employee emp = empRepository.findById(req.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        empPerformance.setEmployee(emp);


        return empPRepository.save(empPerformance);
    }

    public List<EmpPerformance> getAllEmpPerformance() {
        return empPRepository.findAll();
    }

    public EmpPerformance getEmpPerformanceById(String id) {
        return empPRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin!"));
    }

    public EmpPerformance updateEmpPerformance(EmpPerformanceRequest req, String id) {
        EmpPerformance empPerformance = getEmpPerformanceById(id);
        empPerformanceMapper.updateEmpPerformance(req, empPerformance);
        return empPRepository.save(empPerformance);
    }

    public void deleteEmpPerformance(String id) {
        empPRepository.deleteById(id);
    }
}