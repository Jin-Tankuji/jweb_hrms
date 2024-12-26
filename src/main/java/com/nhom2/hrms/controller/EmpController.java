package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.EmpRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.service.EmpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/employees")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmpController {
    private EmpService empService;

    @PostMapping("/create")
    public Employee createEmployee(@RequestBody EmpRequest req) {
        return empService.createEmployee(req);
    }

    @GetMapping("/read")
    ApiResponse<List<Employee>> getEmployees() {
        return ApiResponse.<List<Employee>>builder()
                .result(empService.getEmployees())
                .build();
    }

    @GetMapping("/read/{empId}")
    ApiResponse<Employee> getEmployee(@PathVariable String empId) {
        return ApiResponse.<Employee>builder()
                .result(empService.getEmployee(empId))
                .build();
    }

    @PutMapping("/update/{empId}")
    ApiResponse<Employee> updateEmployee(@PathVariable String empId, @RequestBody EmpRequest req) {
        return ApiResponse.<Employee>builder()
                .result(empService.updateEmployee(req, empId))
                .build();
    }

    @DeleteMapping("/delete/{empId}")
    String deleteEmployee(@PathVariable String empId) {
        empService.deleteEmployee(empId);
        return "Employee has been deleted!";
    }
}
