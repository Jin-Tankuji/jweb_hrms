package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.EmpPerformanceRequest;
import com.nhom2.hrms.entity.EmpPerformance;
import com.nhom2.hrms.service.EmpPerformanceService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/performance")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class EmpPController {

    EmpPerformanceService empPService;

    // Hiển thị danh sách đánh giá hiệu suất nhân viên
    @GetMapping
    public String showPage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size,
                           Model model) {
        // Lấy toàn bộ danh sách đánh giá hiệu suất
        List<EmpPerformance> allEmpPerformances = empPService.getAllEmpPerformance();

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) allEmpPerformances.size() / size);

        // Lấy danh sách hiệu suất theo từng trang
        int start = page * size;
        int end = Math.min(start + size, allEmpPerformances.size());
        List<EmpPerformance> empPerformances = allEmpPerformances.subList(start, end);

        // Lấy danh sách mã nhân viên
        List<String> employeeIdList = empPService.getAllEmployeeId();

        // Truyền dữ liệu vào model
        model.addAttribute("empPerformanceRequest", new EmpPerformanceRequest());
        model.addAttribute("employees", employeeIdList);
        model.addAttribute("empPerformances", empPerformances);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "employee-performance"; // Tên view
    }

    @PostMapping("/create")
    public String createPerformance(@ModelAttribute("empPerformanceRequest") EmpPerformanceRequest req,
                                    RedirectAttributes redirectAttributes) {
        empPService.createEmpPerformance(req);
        redirectAttributes.addFlashAttribute("message", "Thêm mới đánh giá hiệu suất thành công!");
        return "redirect:/performance";
    }

    // Hiển thị form cập nhật đánh giá hiệu suất
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        EmpPerformance empPerformance = empPService.getEmpPerformanceById(id);
        model.addAttribute("empPerformanceRequest", empPerformance);
        return "employee-performance";
    }

    // Xử lý cập nhật đánh giá hiệu suất
    @PostMapping("/update/{id}")
    public String updatePerformance(@PathVariable String id,
                                    @ModelAttribute("empPerformanceRequest") EmpPerformanceRequest req,
                                    RedirectAttributes redirectAttributes) {
        empPService.updateEmpPerformance(req, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật đánh giá hiệu suất thành công!");
        return "redirect:/performance";
    }

    // Xóa đánh giá hiệu suất
    @GetMapping("/delete/{id}")
    public String deletePerformance(@PathVariable String id, RedirectAttributes redirectAttributes) {
        empPService.deleteEmpPerformance(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa đánh giá hiệu suất thành công!");
        return "redirect:/performance";
    }

}