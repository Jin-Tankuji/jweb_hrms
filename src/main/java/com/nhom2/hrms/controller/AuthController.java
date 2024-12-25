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
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.text.ParseException;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AuthController {
    AuthService authService;

    @PostMapping("/login")
    ApiResponse<AuthResponse> authenticate(@RequestBody AuthRequest req) {
        var result = authService.authenticate(req);
        return ApiResponse.<AuthResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/introspect")
    ApiResponse<IntrospectResponse> authenticate(@RequestBody IntrospectRequest req)
            throws ParseException, JOSEException {
        var result = authService.introspect(req);
        return ApiResponse.<IntrospectResponse>builder()
                .result(result.getResult())
                .build();
    }

    @PostMapping("/refresh")
    ApiResponse<AuthResponse> authenticate(@RequestBody RefreshRequest req)
            throws ParseException, JOSEException {
        var result = authService.refreshToken(req);
        return ApiResponse.<AuthResponse>builder()
                .result(result)
                .build();
    }

    @PostMapping("/logout")
    ApiResponse<Void> logout(@RequestBody LogoutRequest req)
            throws ParseException, JOSEException {
        authService.logout(req);
        return ApiResponse.<Void>builder()
                .build();
    }
}
