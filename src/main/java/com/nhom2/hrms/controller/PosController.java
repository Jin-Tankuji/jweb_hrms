package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.PosRequest;
import com.nhom2.hrms.dto.response.ApiResponse;
import com.nhom2.hrms.entity.Position;
import com.nhom2.hrms.service.PosService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/positions")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PosController {
    PosService posService;

    @PostMapping("/create")
    ApiResponse<Position> createPos(@RequestBody PosRequest req) {
        return ApiResponse.<Position>builder()
                .result(posService.createPosition(req))
                .build();
    }

    @GetMapping("/read")
    ApiResponse<List<Position>> getPositions() {
        return ApiResponse.<List<Position>>builder()
                .result(posService.getPositions())
                .build();
    }

    @GetMapping("/read/{posId}")
    ApiResponse<Position> getPosition(@PathVariable String posId) {
        return ApiResponse.<Position>builder()
                .result(posService.getPosition(posId))
                .build();
    }

    @PutMapping("/update/{posId}")
    ApiResponse<Position> updatePosition(@PathVariable String posId, @RequestBody PosRequest req) {
        return ApiResponse.<Position>builder()
                .result(posService.updatePosition(req, posId))
                .build();
    }

    @DeleteMapping("/delete/{posId}")
    String deletePosition(@PathVariable String posId) {
        posService.deletePosition(posId);
        return "Position has been deleted!";
    }
}