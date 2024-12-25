package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.DeptRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.entity.Department;
import com.nhom2.hrms.service.DeptService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/departments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeptController {
    DeptService deptService;

    @PostMapping("/create")
    ApiResponse<Department> createDepart(@RequestBody DeptRequest req) {
        return ApiResponse.<Department>builder()
                .result(deptService.createDepartment(req))
                .build();
    }

    @GetMapping("/read")
    ApiResponse<List<Department>> getDepartments() {
        return ApiResponse.<List<Department>>builder()
                .result(deptService.getDepartments())
                .build();
    }

    @GetMapping("/read/{deptId}")
    ApiResponse<Department> getDepartment(@PathVariable String deptId) {
        return ApiResponse.<Department>builder()
                .result(deptService.getDepartment(deptId))
                .build();
    }

    @PutMapping("/update/{deptId}")
    ApiResponse<Department> updateDepartment(@PathVariable String deptId, @RequestBody DeptRequest req) {
        return ApiResponse.<Department>builder()
                .result(deptService.updateDepartment(req, deptId))
                .build();
    }

    @DeleteMapping("/delete/{deptId}")
    String deleteDepartment(@PathVariable String deptId) {
        deptService.deleteDepartment(deptId);
        return "Department has been deleted";
    }
}
