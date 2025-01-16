package com.nhom2.hrms.configuration;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import jakarta.servlet.http.HttpSession;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class TokenInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(@NonNull HttpServletRequest request,
                             @NonNull HttpServletResponse response,
                             @NonNull Object handler) throws Exception {
        HttpSession session = request.getSession(false);
        String token = (session != null) ? (String) session.getAttribute("token") : null;

        if (token != null) {
            request.setAttribute("Authorization", "Bearer " + token);
            System.out.println("Interceptor đã thêm Token: " + token);
        }

        return true;  // Cho phép request tiếp tục
    }
}
