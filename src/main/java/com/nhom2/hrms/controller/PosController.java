package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.PosRequest;
import com.nhom2.hrms.dto.request.TrainingRequest;
import com.nhom2.hrms.entity.Position;
import com.nhom2.hrms.service.PosService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/position")
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@RequiredArgsConstructor
public class PosController {

    PosService posService;

    // Hiển thị danh sách vị trí
    @GetMapping
    public String showAllPositions(@RequestParam(defaultValue = "0") int page,
                                   @RequestParam(defaultValue = "5") int size,
                                   Model model) {
        List<Position> allPositions = posService.getPositions();
        int totalPages = (int) Math.ceil((double) allPositions.size() / size);


        int start = page * size;
        int end = Math.min(start + size, allPositions.size());
        List<Position> positions = allPositions.subList(start, end);

        model.addAttribute("positionRequest", new PosRequest());
        model.addAttribute("positions", positions);
        model.addAttribute("currentPage", page);
        model.addAttribute("totalPages", totalPages);

        return "position";
    }



    // Xử lý thêm mới vị trí
    @PostMapping("/create")
    public String createPosition(@ModelAttribute("posRequest") PosRequest req,
                                 RedirectAttributes redirectAttributes) {
        posService.createPosition(req);
        redirectAttributes.addFlashAttribute("successMessage", "Thêm vị trí mới thành công!");
        return "redirect:/position";
    }

    // Hiển thị form cập nhật vị trí
    @GetMapping("/update/{id}")
    public String showUpdateForm(@PathVariable String id, Model model) {
        Position position = posService.getPosition(id);
        model.addAttribute("posRequest", position);
        return "position";
    }

    // Xử lý cập nhật vị trí
    @PostMapping("/update/{id}")
    public String updatePosition(@PathVariable String id,
                                 @ModelAttribute("posRequest") PosRequest req,
                                 RedirectAttributes redirectAttributes) {
        posService.updatePosition(req, id);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật vị trí thành công!");
        return "redirect:/position";
    }

    // Xóa vị trí
    @GetMapping("/delete/{id}")
    public String deletePosition(@PathVariable String id, RedirectAttributes redirectAttributes) {
        posService.deletePosition(id);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa vị trí thành công!");
        return "redirect:/position";
    }

    // Xem chi tiết vị trí
    @GetMapping("/detail/{id}")
    public String viewPositionDetail(@PathVariable String id, Model model) {
        Position position = posService.getPosition(id);
        model.addAttribute("position", position);
        return "position/detail";
    }

}