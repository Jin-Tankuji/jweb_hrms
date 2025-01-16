package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.AuthRequest;
import com.nhom2.hrms.dto.request.LogoutRequest;
import com.nhom2.hrms.dto.response.AuthResponse;
import com.nhom2.hrms.entity.Training;
import com.nhom2.hrms.service.AuthService;
import com.nhom2.hrms.service.TrainingService;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import java.util.List;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;
    TrainingService trainingService;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }

    @GetMapping("/index")
    public String showHomePage(Model model) {
        List<Training> trainings = trainingService.getTrainings();
        model.addAttribute("trainings", trainings);
        return "index";
    }

    @PostMapping("/authenticate")
    public String authenticate(@RequestParam String username,
                                @RequestParam String password,
                                HttpSession session,
                                Model model) {
        try {
            // Tạo request từ form
            AuthRequest req = new AuthRequest(username, password);

            // Gọi service để kiểm tra đăng nhập
            AuthResponse response = authService.authenticate(req);

            // Lưu token vào session nếu đăng nhập thành công
            session.setAttribute("token", response.getToken());
            System.out.println("Đăng nhập thành công, token: " + response.getToken());
            return "redirect:/index"; // Chuyển hướng đến index nếu thành công
        } catch (RuntimeException e) {
            // Xử lý lỗi: Hiển thị thông báo lỗi trên giao diện
            model.addAttribute("error", e.getMessage()); // Đưa thông báo lỗi vào model
            return "redirect:/?error=true"; // Trả về lại trang login với thông báo lỗi
        }
    }

    @GetMapping("/logout")
    public String logout(HttpSession session, Model model) {
        String token = (String) session.getAttribute("token");
        if (token != null) {
            try {
                LogoutRequest logoutRequest = new LogoutRequest(token);
                authService.logout(logoutRequest);  // Gọi hàm logout
            } catch (Exception e) {
                model.addAttribute("error", "Đã xảy ra lỗi khi đăng xuất. Vui lòng thử lại!");
                return "index";
            }
            session.invalidate();  // Xóa session sau khi đăng xuất thành công
        }
        return "login";
    }
}

//    @PostMapping("/introspect")
//    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest req)
//            throws ParseException, JOSEException {
//        var result = authService.introspect(req);
//        return ApiResponse.<IntrospectResponse>builder()
//                .result(result.getResult())
//                .build();
//    }
//
//    @PostMapping("/refresh")
//    ApiResponse<AuthResponse> authenticate(@RequestBody RefreshRequest req)
//            throws ParseException, JOSEException {
//        var result = authService.refreshToken(req);
//        return ApiResponse.<AuthResponse>builder()
//                .result(result)
//                .build();
//    }

//    @PostMapping("/logout")
//    ApiResponse<Void> logout(@RequestBody LogoutRequest req)
//            throws ParseException, JOSEException {
//        authService.logout(req);
//        return ApiResponse.<Void>builder()
//                .build();
//    }

