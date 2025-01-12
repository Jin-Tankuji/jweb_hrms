package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.EmpRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.service.EmpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Controller
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmpController {
    private EmpService empService;

//    @PostMapping("/create")
//    public Employee createEmployee(@RequestBody EmpRequest req) {
//        return empService.createEmployee(req);
//    }
// lấy form
@GetMapping("/employee")
public String ShowAddPage(Model model) {
    Employee employee = new Employee();
    model.addAttribute("employee", employee);
    return "employee";
}
@PostMapping("/create")
public String createEmployee(@ModelAttribute EmpRequest empRequest, Model model) {
    Employee employee = empService.createEmployee(empRequest);
    model.addAttribute("employee", employee);
    return "employee";
}


//    @GetMapping("/read")
//    ApiResponse<List<Employee>> getEmployees() {
//        return ApiResponse.<List<Employee>>builder()
//                .result(empService.getEmployees())
//                .build();
//    }
@GetMapping("/read")
public String getEmployees(Model model) {
    List<Employee> employee = empService.getEmployees();
    model.addAttribute("employees", employee);
    return "index";  // Chuyển tới view 'employee-header.html'
}

//    @GetMapping("/read/{empId}")
//    ApiResponse<Employee> getEmployee(@PathVariable String empId) {
//        return ApiResponse.<Employee>builder()
//                .result(empService.getEmployee(empId))
//                .build();
//    }
@GetMapping("/read/{empId}")
public String getEmployee(@PathVariable String empId, Model model) {
    Employee employee = empService.getEmployee(empId);
    model.addAttribute("employees", employee);
    return "employee";  // Chuyển tới view 'employee-detail.html'
}

//    @PutMapping("/update/{empId}")
//    ApiResponse<Employee> updateEmployee(@PathVariable String empId, @RequestBody EmpRequest req) {
//        return ApiResponse.<Employee>builder()
//                .result(empService.updateEmployee(req, empId))
//                .build();
//    }
@PutMapping("/update/{empId}")
public String updateEmployee(@PathVariable String empId, @ModelAttribute EmpRequest req, Model model) {
    Employee employee = empService.updateEmployee(req, empId);
    model.addAttribute("employees", employee);
    return "employee";  // Chuyển tới view 'employee-detail.html'
}
    @DeleteMapping("/delete/{empId}")
    public String deleteEmployee(@PathVariable String empId) {
        empService.deleteEmployee(empId);
        return "redirect:/employee/read";  // Quay lại danh sách sau khi xóa
    }

//    @DeleteMapping("/delete/{empId}")
//    String deleteEmployee(@PathVariable String empId) {
//        empService.deleteEmployee(empId);
//        return "Employee has been deleted!";
//    }
}
