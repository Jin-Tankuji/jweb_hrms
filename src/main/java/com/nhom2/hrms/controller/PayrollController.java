package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.PayrollRequest;
import com.nhom2.hrms.entity.Payroll;
import com.nhom2.hrms.service.PayrollService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/payroll")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PayrollController {

    PayrollService payrollService;

    // Hiển thị danh sách bảng tính lương
    @GetMapping
    public String showAllPayrolls(@RequestParam(defaultValue = "0") int page,
                                  @RequestParam(defaultValue = "5") int size,
                                  Model model) {
        // Lấy toàn bộ danh sách bảng tính lương
        List<Payroll> allPayrolls = payrollService.getPayrolls();

        // Tính toán số trang
        int totalPages = (int) Math.ceil((double) allPayrolls.size() / size);

        // Lấy danh sách tính lương theo từng trang
        int start = page * size;
        int end = Math.min(start + size, allPayrolls.size());
        List<Payroll> payrolls = allPayrolls.subList(start, end);

        // Lấy danh sách mã nhân viên
        List<String> employeeIdList = payrollService.getAllEmployeeId();

        // Truyền dữ liệu vào model
        model.addAttribute("payrollRequest", new PayrollRequest());
        model.addAttribute("employees", employeeIdList);
        model.addAttribute("payrolls", payrolls);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "payroll";
    }

    // Xử lý thêm mới bảng tính lương
    @PostMapping("/create")
    public String createPayroll(@ModelAttribute("payrollRequest") PayrollRequest req,
                                RedirectAttributes redirectAttributes) {
        payrollService.createPayroll(req);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm bảng tính lương thành công!");
        return "redirect:/payroll";
    }

    // Hiển thị form chỉnh sửa bảng tính lương
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Payroll payroll = payrollService.getPayroll(id);
        model.addAttribute("payrollRequest", payroll);
        return "payroll/edit";
    }

    // Xử lý cập nhật bảng tính lương
    @PostMapping("/update/{id}")
    public String updatePayroll(@PathVariable String id,
                                @ModelAttribute("payrollRequest") PayrollRequest req,
                                RedirectAttributes redirectAttributes) {
        payrollService.updatePayroll(req, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật bảng tính lương thành công!");
        return "redirect:/payroll";
    }

    // Xóa bảng tính lương
    @GetMapping("/delete/{id}")
    public String deletePayroll(@PathVariable String id, RedirectAttributes redirectAttributes) {
        payrollService.deletePayroll(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa bảng tính lương thành công!");
        return "redirect:/payroll";
    }

    // Xem chi tiết bảng tính lương
    @GetMapping("/detail/{id}")
    public String viewPayrollDetail(@PathVariable String id, Model model) {
        Payroll payroll = payrollService.getPayroll(id);
        model.addAttribute("payroll", payroll);
        return "payroll/detail";
    }
}
