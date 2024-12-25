package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.LeaveRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.entity.Leave;
import com.nhom2.hrms.service.LeaveService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/leaves")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveController {
    LeaveService leaveService;

    @PostMapping("/create")
    ApiResponse<Leave> createLeave(@RequestBody LeaveRequest req) {
        return ApiResponse.<Leave>builder()
                .result(leaveService.createLeave(req))
                .build();
    }

    @GetMapping("/read")
    ApiResponse<List<Leave>> getLeaves() {
        return ApiResponse.<List<Leave>>builder()
                .result(leaveService.getLeaves())
                .build();
    }

    @GetMapping("/read/{leaveId}")
    ApiResponse<Leave> getLeave(@PathVariable String leaveId) {
        return ApiResponse.<Leave>builder()
                .result(leaveService.getLeave(leaveId))
                .build();
    }

    @PutMapping("/update/{leaveId}")
    ApiResponse<Leave> updateLeave(@PathVariable String leaveId, @RequestBody LeaveRequest req) {
        return ApiResponse.<Leave>builder()
                .result(leaveService.updateLeave(req, leaveId))
                .build();
    }

    @DeleteMapping("/delete/{leaveId}")
    String deleteLeave(@PathVariable String leaveId) {
        leaveService.deleteLeave(leaveId);
        return "Leave info has been deleted";
    }
}
