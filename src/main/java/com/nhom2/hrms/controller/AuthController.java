package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.AuthRequest;
import com.nhom2.hrms.dto.request.IntrospectRequest;
import com.nhom2.hrms.dto.request.LogoutRequest;
import com.nhom2.hrms.dto.request.RefreshRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.dto.response.AuthResponse;
import com.nhom2.hrms.dto.response.IntrospectResponse;
import com.nhom2.hrms.service.AuthService;
import com.nimbusds.jose.JOSEException;
import jakarta.servlet.http.HttpSession;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

@Controller
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @GetMapping("/")
    public String showLoginPage() {
        return "login";
    }
    @GetMapping("/index")
    public String showIndexPage() {
        return "index";
    }

    @PostMapping("/login")
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
            return "index"; // Chuyển hướng đến index nếu thành công
        } catch (RuntimeException e) {
            // Xử lý lỗi: Hiển thị thông báo lỗi trên giao diện
            model.addAttribute("error", e.getMessage()); // Đưa thông báo lỗi vào model
            return "login"; // Trả về lại trang login với thông báo lỗi
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

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest req)
            throws ParseException, JOSEException {
        authService.logout(req);
        return ApiResponse.<Void>builder()
                .build();
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
