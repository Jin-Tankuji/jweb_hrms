package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.UserRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.dto.response.UserResponse;
import com.nhom2.hrms.entity.User;
import com.nhom2.hrms.service.UserService;
import jakarta.validation.Valid;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/users")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class UserController {
    UserService userService;

    @PostMapping("/create")
    User createUser(@RequestBody @Valid UserRequest req) {
        return userService.createUser(req);
    }

    @GetMapping("/read")
    ApiResponse<List<UserResponse>> getUsers() {
        var authentication = SecurityContextHolder.getContext().getAuthentication();

        log.info("Authentication : {}", authentication.getName());
        authentication.getAuthorities().forEach(grantedAuthority -> log.info("GrantedAuthority : {}", grantedAuthority.getAuthority()));

        return ApiResponse.<List<UserResponse>>builder()
                .result(userService.getUsers())
                .build();
    }

    @GetMapping("/myInfo")
    ApiResponse<UserResponse> getMyInfo() {
        return ApiResponse.<UserResponse>builder()
                .result(userService.getMyInfo())
                .build();
    }

    @GetMapping("/read/{userId}")
    User getUser(@PathVariable String userId) {
        return userService.getUser(userId);
    }

    @PutMapping("/update/{userId}")
    User updateUser(@PathVariable String userId, @RequestBody Map<String, String> payload) {
        String newPassword = payload.get("newPassword");
        return userService.updateUser(userId, newPassword);
    }

    @DeleteMapping("/delete/{userId}")
    String deleteUser(@PathVariable String userId) {
        userService.deleteUser(userId);
        return "User has been deleted!";
    }
}
