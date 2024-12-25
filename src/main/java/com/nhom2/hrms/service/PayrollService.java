package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.PayrollRequest;
import com.nhom2.hrms.entity.Payroll;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.mapper.PayrollMapper;
import com.nhom2.hrms.repository.PayrollRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PayrollService {
    PayrollRepository payrollRepository;
    PayrollMapper payrollMapper;

    // Creates a new payroll on the request and saves it to the database
    public Payroll createPayroll(PayrollRequest req) {
        Payroll payroll = payrollMapper.toPayroll(req);

        Employee emp = new Employee();
        emp.setEmployeeId(req.getEmployee().getEmployeeId());
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

    // Updates an existing payroll on the provided request and ID
    public Payroll updatePayroll(PayrollRequest req, String id) {
        Payroll payroll = getPayroll(id);

        Employee emp = new Employee();
        emp.setEmployeeId(req.getEmployee().getEmployeeId());
        payroll.setEmployee(emp);
        payrollMapper.updatePayroll(req, payroll);

        return payrollRepository.save(payroll);
    }

    // Deletes a payroll by its ID
    public void deletePayroll(String id) {
        payrollRepository.deleteById(id);
    }
}