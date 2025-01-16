package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.DeptRequest;
import com.nhom2.hrms.entity.Department;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.service.DeptService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Optional;

@Controller
@RequestMapping("/department")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class DeptController {

    DeptService deptService;

    // Hiển thị danh sách phòng ban
    @GetMapping
    public String showAllDepartments(@RequestParam(defaultValue = "0") int page,
                                     @RequestParam(defaultValue = "5") int size,
                                     Model model) {
        List<Department> allDepartments = deptService.getDepartments();

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) allDepartments.size() / size);

        // Lấy danh sách phòng ban cho từng trang
        int start = page * size;
        int end = Math.min(start + size, allDepartments.size());
        List<Department> departments = allDepartments.subList(start, end);

        model.addAttribute("deptRequest", new DeptRequest());
        model.addAttribute("departments", departments);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "department";
    }

    // Xử lý thêm mới phòng ban
    @PostMapping("/create")
    public String createDepartment(@ModelAttribute("deptRequest") DeptRequest req,
                                   RedirectAttributes redirectAttributes) {
        deptService.createDepartment(req);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm phòng ban mới thành công!");
        return "redirect:/department";
    }

    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Department department = deptService.getDepartment(id);
        DeptRequest deptRequest = new DeptRequest();
        deptRequest.setDepartmentId(department.getDepartmentId());
        deptRequest.setName(department.getName());
        deptRequest.setDescription(department.getDescription());

        model.addAttribute("deptRequest", deptRequest);
        return "updateDepartment";
    }

    // Xử lý form cập nhật phòng ban
    @PostMapping("/update")
    public String updateDepartment(@ModelAttribute("deptRequest") DeptRequest deptRequest) {
        deptService.updateDepartment(deptRequest.getDepartmentId(), deptRequest);
        return "redirect:/department";
    }

    // Xóa phòng ban
    @GetMapping("/delete/{id}")
    public String deleteDepartment(@PathVariable String id, RedirectAttributes redirectAttributes) {
        deptService.deleteDepartment(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa phòng ban thành công!");
        return "redirect:/department";
    }

}
