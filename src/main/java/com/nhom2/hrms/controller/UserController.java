package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.UserRequest;
import com.nhom2.hrms.dto.response.UserResponse;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.entity.User;
import com.nhom2.hrms.enums.Role;
import com.nhom2.hrms.service.UserService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.ui.Model;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Slf4j
@Controller
@RequestMapping("/user")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {

    UserService userService;

    // Hiển thị danh sách người dùng
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping
    public String showAllUsers(@RequestParam(defaultValue = "0") int page,
                               @RequestParam(defaultValue = "5") int size,
                               Model model) {
        // Lấy toàn bộ danh sách người dùng
        List<User> allUsers = userService.getAllUserHaveEmployeeId();

        // Tính toán tổng số trang
        int totalPages = (int) Math.ceil((double) allUsers.size() / size);

        // Lấy danh sách người dùng theo từng trang
        int start = page * size;
        int end = Math.min(start + size, allUsers.size());
        List<User> userList = allUsers.subList(start, end);

        // Lấy danh sách mã nhân viên
        List<String> employeeIdList = userService.getAllEmployeeId();

        // Truyền dữ liệu vào model
        model.addAttribute("userRequest", new UserRequest());
        model.addAttribute("rolesList", Role.values());
        model.addAttribute("employees", employeeIdList);
        model.addAttribute("userList", userList);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "user";
    }


    // Thêm mới người dùng
    //@PreAuthorize("hasRole('ADMIN')")
    @PostMapping("/create")
    public String createUser(@ModelAttribute("userRequest") UserRequest req, RedirectAttributes redirectAttributes) {
        try {
            userService.createUser(req);
            redirectAttributes.addFlashAttribute("successMessage", "Thêm người dùng thành công!");
        } catch (RuntimeException e) {
            redirectAttributes.addFlashAttribute("errorMessage", e.getMessage());
        }
        return "redirect:/user";
    }

    // Xem thông tin cá nhân người dùng (chỉ cho chính họ)
    @GetMapping("/profile")
    public String getMyInfo(Model model) {
        UserResponse userResponse = userService.getMyInfo();
        model.addAttribute("user", userResponse);
        return "profile";
    }

    // Hiển thị form cập nhật mật khẩu (chỉ cho chính họ)
    @GetMapping("/update/{id}")
    @PreAuthorize("authentication.name == #id")
    public String showUpdatePasswordForm(@PathVariable String id, Model model) {
        model.addAttribute("userId", id);
        return "user";
    }

    // Cập nhật mật khẩu người dùng (chỉ cho chính họ)
    @PostMapping("/update/{id}")
    @PreAuthorize("authentication.name == #id")
    public String updatePassword(@PathVariable String id, @RequestParam String newPassword,
                                 RedirectAttributes redirectAttributes) {
        userService.updateUser(id, newPassword);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật mật khẩu thành công!");
        return "redirect:/profile";
    }

    // Xóa người dùng (chỉ Admin)
    //@PreAuthorize("hasRole('ADMIN')")
    @GetMapping("/delete/{id}")
    public String deleteUser(@PathVariable String id, RedirectAttributes redirectAttributes) {
        userService.deleteUser(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa người dùng thành công!");
        return "redirect:/user";
    }
}
