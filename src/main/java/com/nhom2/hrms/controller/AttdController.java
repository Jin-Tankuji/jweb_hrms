package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.AttdRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.entity.Attendance;
import com.nhom2.hrms.service.AttdService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/attendances")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttdController {
    AttdService attdService;

    @PostMapping("/create")
    ApiResponse<Attendance> createAttendance(@RequestBody AttdRequest req) {
        return ApiResponse.<Attendance>builder()
                .result(attdService.createAttendance(req))
                .build();
    }

    @GetMapping("/read")
    ApiResponse<List<Attendance>> getAttendances() {
        return ApiResponse.<List<Attendance>>builder()
                .result(attdService.getAttendances())
                .build();
    }

    @GetMapping("/read/{attdId}")
    ApiResponse<Attendance> getAttendance(@PathVariable String attdId) {
        return ApiResponse.<Attendance>builder()
                .result(attdService.getAttendance(attdId))
                .build();
    }

    @GetMapping("/read/myAttendance/{employeeId}")
    ApiResponse<List<Attendance>> myAttendance(@PathVariable String employeeId) {
        List<Attendance> attendanceList = attdService.getAttendanceByEmployeeId(employeeId);
        return ApiResponse.<List<Attendance>>builder()
                .result(attendanceList)
                .build();
    }

    @PutMapping("/update/{attdId}")
    ApiResponse<Attendance> updateAttendance(@PathVariable String attdId, @RequestBody AttdRequest req) {
        return ApiResponse.<Attendance>builder()
                .result(attdService.updateAttendance(req, attdId))
                .build();
    }

    @DeleteMapping("/delete/{attdId}")
    String deleteAttendance(@PathVariable String attdId) {
        attdService.deleteAttendance(attdId);
        return "Attendance has been deleted";
    }
}
