package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.PayrollRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.entity.Attendance;
import com.nhom2.hrms.entity.Payroll;
import com.nhom2.hrms.service.PayrollService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/payrolls")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PayrollController {
    PayrollService payrollService;

    @PostMapping("/create")
    ApiResponse<Payroll> createPayroll(@RequestBody PayrollRequest req) {
        return ApiResponse.<Payroll>builder()
                .result(payrollService.createPayroll(req))
                .build();
    }

    @GetMapping("/read")
    ApiResponse<List<Payroll>> getPayrolls() {
        return ApiResponse.<List<Payroll>>builder()
                .result(payrollService.getPayrolls())
                .build();
    }

    @GetMapping("/read/{payrollId}")
    ApiResponse<Payroll> getPayroll(@PathVariable String payrollId) {
        return ApiResponse.<Payroll>builder()
                .result(payrollService.getPayroll(payrollId))
                .build();
    }
    @GetMapping("/read/myPayroll/{employeeId}")
    ApiResponse<List<Payroll>> myPayroll(@PathVariable String employeeId) {
        List<Payroll> payrollList = payrollService.getPayrollByEmployeeId(employeeId);
        return ApiResponse.<List<Payroll>>builder()
            .result(payrollList)
            .build();
    }

    @PutMapping("/update/{payrollId}")
    ApiResponse<Payroll> updatePayroll(@PathVariable String payrollId, @RequestBody PayrollRequest req) {
        return ApiResponse.<Payroll>builder()
                .result(payrollService.updatePayroll(req, payrollId))
                .build();
    }

    @DeleteMapping("/delete/{payrollId}")
    String deletePayroll(@PathVariable String payrollId) {
        payrollService.deletePayroll(payrollId);
        return "Payroll has been deleted";
    }
}
