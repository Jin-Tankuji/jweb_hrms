package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.AttdRequest;
import com.nhom2.hrms.entity.Attendance;
import com.nhom2.hrms.service.AttdService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/attendance")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttdController {
    AttdService attdService;

    // Hiển thị danh sách bảng chấm công
    @GetMapping
    public String showPage(@RequestParam(defaultValue = "0") int page,
                           @RequestParam(defaultValue = "5") int size,
                           Model model) {
        List<String> empIdList = attdService.getAllEmployeeId();
        List<Attendance> allAttendances = attdService.getAttendances();

        // Tính toán số trang
        int totalPages = (int) Math.ceil((double) allAttendances.size() / size);

        // Lấy danh sách phân trang
        int start = page * size;
        int end = Math.min(start + size, allAttendances.size());
        List<Attendance> attendances = allAttendances.subList(start, end);

        model.addAttribute("attdRequest", new AttdRequest());
        model.addAttribute("empIds", empIdList);
        model.addAttribute("attendances", attendances);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "attendance";
    }

    // Xử lý thêm mới bảng chấm công
    @PostMapping("/create")
    public String createAttendance(@ModelAttribute("attdRequest") AttdRequest req,
                                   RedirectAttributes redirectAttributes) {
        attdService.createAttendance(req);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm bảng chấm công thành công!");
        return "redirect:/attendance";
    }

    // Hiển thị form cập nhật bảng chấm công
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Attendance attendance = attdService.getAttendance(id);
        model.addAttribute("attdRequest", attendance);
        return "attendance";
    }

    // Xử lý cập nhật bảng chấm công
    @PostMapping("/update/{id}")
    public String updateAttendance(@PathVariable String id,
                                   @ModelAttribute("attdRequest") AttdRequest req,
                                   RedirectAttributes redirectAttributes) {
        attdService.updateAttendance(req, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật bảng chấm công thành công!");
        return "redirect:/attendance";
    }

    // Xóa bảng chấm công
    @GetMapping("/delete/{id}")
    public String deleteAttendance(@PathVariable String id, RedirectAttributes redirectAttributes) {
        attdService.deleteAttendance(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa bảng chấm công thành công!");
        return "redirect:/attendance";
    }

    // Xem chi tiết bảng chấm công
    @GetMapping("/detail/{id}")
    public String viewAttendanceDetail(@PathVariable String id, Model model) {
        Attendance attendance = attdService.getAttendance(id);
        model.addAttribute("attendance", attendance);
        return "attendance/detail";
    }
}
