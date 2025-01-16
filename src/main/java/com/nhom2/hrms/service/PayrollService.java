package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.PayrollRequest;
import com.nhom2.hrms.entity.Attendance;
import com.nhom2.hrms.entity.Payroll;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.mapper.PayrollMapper;
import com.nhom2.hrms.repository.EmpRepository;
import com.nhom2.hrms.repository.PayrollRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PayrollService {
    PayrollRepository payrollRepository;
    PayrollMapper payrollMapper;
    EmpRepository empRepository;

    public List<String> getAllEmployeeId() {
        return empRepository.findAll().stream()
                .map(Employee::getEmployeeId)
                .collect(Collectors.toList());
    }

    // Creates a new payroll on the request and saves it to the database
    public Payroll createPayroll(PayrollRequest req) {
        Payroll payroll = payrollMapper.toPayroll(req);

        Employee emp = empRepository.findById(req.getEmployeeId())
                .orElseThrow(() -> new RuntimeException("Không tìm thấy nhân viên"));

        payroll.setEmployee(emp);

        return payrollRepository.save(payroll);
    }

    // Retrieves all payrolls from the database
    public List<Payroll> getPayrolls() {
        return payrollRepository.findAll();
    }

    // Retrieves a specific payroll by its ID, throws an exception if not found
    public Payroll getPayroll(String id) {
        return payrollRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bảng tính lương"));
    }

    public List<Payroll> getPayrollByEmployeeId(String employeeId) {
        return payrollRepository.findByEmployeeEmployeeId(employeeId);
    }
    // Updates an existing payroll on the provided request and ID
    public Payroll updatePayroll(PayrollRequest req, String id) {
        Payroll payroll = getPayroll(id);

        Employee emp = new Employee();
        emp.setEmployeeId(req.getEmployeeId());
        payroll.setEmployee(emp);
        payrollMapper.updatePayroll(req, payroll);

        return payrollRepository.save(payroll);
    }

    // Deletes a payroll by its ID
    public void deletePayroll(String id) {
        payrollRepository.deleteById(id);
    }
}
