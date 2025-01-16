package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.AttdRequest;
import com.nhom2.hrms.dto.request.LeaveRequest;
import com.nhom2.hrms.entity.Leave;
import com.nhom2.hrms.service.LeaveService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/leave")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveController {

    LeaveService leaveService;

    // Hiển thị danh sách đơn nghỉ phép
    @GetMapping
    public String showAllLeaves(@RequestParam(defaultValue = "0") int page,
                                @RequestParam(defaultValue = "5") int size,
                                Model model) {
        // Lấy danh sách tất cả đơn nghỉ phép
        List<Leave> allLeaves = leaveService.getLeaves();

        // Tính tổng số trang
        int totalPages = (int) Math.ceil((double) allLeaves.size() / size);

        // Lấy danh sách đơn nghỉ phép cho từng trang
        int start = page * size;
        int end = Math.min(start + size, allLeaves.size());
        List<Leave> leaves = allLeaves.subList(start, end);

        // Lấy danh sách mã nhân viên
        List<String> empIdList = leaveService.getAllEmployeeId();

        // Truyền dữ liệu vào model
        model.addAttribute("leaveRequest", new LeaveRequest());
        model.addAttribute("empIds", empIdList);
        model.addAttribute("leaves", leaves);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "leave"; // View: leave.html
    }


    // Xử lý thêm mới đơn nghỉ phép
    @PostMapping("/create")
    public String createLeave(@ModelAttribute("leaveRequest") LeaveRequest req,
                              RedirectAttributes redirectAttributes) {
        leaveService.createLeave(req);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm đơn nghỉ phép thành công!");
        return "redirect:/leave";
    }

    // Hiển thị form cập nhật đơn nghỉ phép
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Leave leave = leaveService.getLeave(id);
        model.addAttribute("leaveRequest", leave);
        return "leave";
    }

    // Xử lý cập nhật đơn nghỉ phép
    @PostMapping("/update/{id}")
    public String updateLeave(@PathVariable String id,
                              @ModelAttribute("leaveRequest") LeaveRequest req,
                              RedirectAttributes redirectAttributes) {
        leaveService.updateLeave(req, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật đơn nghỉ phép thành công!");
        return "redirect:/leave";
    }

    // Xóa đơn nghỉ phép
    @GetMapping("/delete/{id}")
    public String deleteLeave(@PathVariable String id, RedirectAttributes redirectAttributes) {
        leaveService.deleteLeave(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa đơn nghỉ phép thành công!");
        return "redirect:/leave";
    }

    // Xem chi tiết đơn nghỉ phép
    @GetMapping("/detail/{id}")
    public String viewLeaveDetail(@PathVariable String id, Model model) {
        Leave leave = leaveService.getLeave(id);
        model.addAttribute("leave", leave);
        return "leave/detail";
    }
}
