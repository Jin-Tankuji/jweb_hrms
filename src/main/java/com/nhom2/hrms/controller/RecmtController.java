package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.AttdRequest;
import com.nhom2.hrms.dto.request.RecmtRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.entity.Attendance;
import com.nhom2.hrms.entity.Recruitment;
import com.nhom2.hrms.service.RecmtService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/recruitments")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecmtController {
    RecmtService recmtService;

    @PostMapping("/create")
    ApiResponse<Recruitment> createRecruitment(@RequestBody RecmtRequest req) {
        return ApiResponse.<Recruitment>builder()
                .result(recmtService.createRecruitment(req))
                .build();
    }

    @GetMapping("/read")
    ApiResponse<List<Recruitment>> getRecruitments() {
        return ApiResponse.<List<Recruitment>>builder()
                .result(recmtService.getRecruitments())
                .build();
    }

    @GetMapping("/read/{recmtId}")
    ApiResponse<Recruitment> getRecruitment(@PathVariable String recmtId) {
        return ApiResponse.<Recruitment>builder()
                .result(recmtService.getRecruitment(recmtId))
                .build();
    }

    @PutMapping("/update/{recmtId}")
    ApiResponse<Recruitment> updateRecruitment(@PathVariable String recmtId, @RequestBody RecmtRequest req) {
        return ApiResponse.<Recruitment>builder()
                .result(recmtService.updateRecruitment(req, recmtId))
                .build();
    }

    @DeleteMapping("/delete/{recmtId}")
    String deleteRecruitment(@PathVariable String recmtId) {
        recmtService.deleteRecruitment(recmtId);
        return "Recruitment has been deleted";
    }
}
