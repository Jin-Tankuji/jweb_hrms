package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.DeptRequest;
import com.nhom2.hrms.dto.request.EmpRequest;
import com.nhom2.hrms.entity.Department;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.entity.Position;
import com.nhom2.hrms.mapper.EmpMapper;
import com.nhom2.hrms.service.EmpService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/employee")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class EmpController {

    EmpService empService;
    EmpMapper empMapper;

    // Hiển thị danh sách nhân viên (không phân trang)
    @GetMapping
    public String showEmployees(Model model) {
        // Lấy toàn bộ danh sách nhân viên
        List<Employee> employees = empService.getEmployees();

        // Lấy danh sách phòng ban và vị trí
        List<String> departmentIdList = empService.getAllDepartmentId();
        List<String> positionIdList = empService.getAllPositionId();

        // Truyền dữ liệu vào model
        model.addAttribute("empRequest", new EmpRequest());
        model.addAttribute("department", departmentIdList);
        model.addAttribute("position", positionIdList);
        model.addAttribute("employees", employees);

        return "employee"; // Tên view HTML
    }

    // Thêm nhân viên mới
    @PostMapping("/create")
    public String createEmployee(@ModelAttribute("empRequest") EmpRequest req, RedirectAttributes redirectAttributes) {
        empService.createEmployee(req);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm nhân viên thành công!");
        return "redirect:/employee";
    }

    // Xem danh sách nhân viên
    @GetMapping("/read")
    public String getEmployees(Model model) {
        List<Employee> employees = empService.getEmployees();
        model.addAttribute("employees", employees);
        return "employee";
    }

    // Xem thông tin chi tiết một nhân viên
    @GetMapping("/read/{empId}")
    public String getEmployee(@PathVariable String empId, Model model) {
        Employee employee = empService.getEmployee(empId);
        model.addAttribute("employees", employee);
        return "employee";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        // Lấy dữ liệu nhân viên từ database
        Employee employee = empService.getEmployee(id);
        List<String> departmentIdList = empService.getAllDepartmentId();
        List<String> positionIdList = empService.getAllPositionId();

        // Chuyển dữ liệu sang EmpRequest để hiển thị trong form
        EmpRequest empRequest = empMapper.toEmpRequest(employee);

        // Đẩy dữ liệu vào model
        model.addAttribute("department", departmentIdList);
        model.addAttribute("position", positionIdList);
        model.addAttribute("empRequest", empRequest);

        return "updateEmployee";
    }

    // Xử lý cập nhật thông tin nhân viên
    @PostMapping("/update/{id}")
    public String updateEmployee(@PathVariable String id,
                                 @ModelAttribute("empRequest") EmpRequest empRequest,
                                 RedirectAttributes redirectAttributes) {
        empService.updateEmployee(id, empRequest);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật nhân viên thành công!");
        return "redirect:/employee";
    }

    // Xóa nhân viên
    @GetMapping("/delete/{empId}")
    public String deleteEmployee(@PathVariable String empId, RedirectAttributes redirectAttributes) {
        empService.deleteEmployee(empId);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa nhân viên thành công!");
        return "redirect:/employee";
    }

    // Tìm kiếm nhân viên theo tên
    @GetMapping("/search")
    public String searchEmployee(@RequestParam("firstName") String firstName, Model model) {
        // Tìm kiếm nhân viên theo tên
        List<Employee> employees = empService.searchEmployee(firstName);

        // Truyền dữ liệu vào model
        model.addAttribute("employees", employees);
        model.addAttribute("firstName", firstName);

        return "employee";
    }
}
