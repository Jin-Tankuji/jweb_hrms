package com.nhom2.hrms.controller;

import com.nhom2.hrms.dto.request.TrainingRequest;
import com.nhom2.hrms.entity.Training;
import com.nhom2.hrms.mapper.TrainingMapper;
import com.nhom2.hrms.service.TrainingService;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;

@Controller
@RequestMapping("/training")
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class TrainingController {

    TrainingService trainingService;
    private final TrainingMapper trainingMapper;

    // Hiển thị danh sách chương trình đào tạo
    @GetMapping
    public String showTrainingList(Model model) {
        // Lấy toàn bộ danh sách chương trình đào tạo
        List<Training> trainings = trainingService.getTrainings();

        // Truyền dữ liệu vào model
        model.addAttribute("trainRequest", new TrainingRequest());
        model.addAttribute("trainings", trainings);

        return "training"; // Tên view HTML
    }

    // Xử lý tạo mới chương trình đào tạo
    @PostMapping("/create")
    public String createTraining(@ModelAttribute("training") TrainingRequest req, RedirectAttributes redirectAttributes) {
        trainingService.createTraining(req);
        redirectAttributes.addFlashAttribute("successMessage", "Tạo chương trình đào tạo thành công!");
        return "redirect:/training";
    }

    // Hiển thị form chỉnh sửa chương trình đào tạo
    @GetMapping("/update/{id}")
    public String showEditForm(@PathVariable String id, Model model) {
        Training training = trainingService.getTraining(id);

        TrainingRequest trainingRequest = trainingMapper.toTrainRequest(training);

        model.addAttribute("trainRequest", trainingRequest);
        return "updateTraining";
    }

    // Xử lý cập nhật chương trình đào tạo
    @PostMapping("/update/{id}")
    public String updateTraining(@PathVariable String id,
                                 @ModelAttribute("trainRequest") TrainingRequest req,
                                 RedirectAttributes redirectAttributes) {
        trainingService.updateTraining(id, req);
        redirectAttributes.addFlashAttribute("successMessage", "Cập nhật chương trình đào tạo thành công!");
        return "redirect:/training";
    }

    // Xử lý xóa chương trình đào tạo
    @GetMapping("/delete/{trainId}")
    public String deleteTraining(@PathVariable String trainId, RedirectAttributes redirectAttributes) {
        trainingService.deleteTraining(trainId);
        redirectAttributes.addFlashAttribute("successMessage", "Xóa chương trình đào tạo thành công!");
        return "redirect:/training";
    }

    // Xử lý tìm kiếm chương trình đào tạo
    @GetMapping("/search")
    public String searchTraining(@RequestParam("courseName") String courseName, Model model) {
        List<Training> trainingList = trainingService.searchTrainingByCourseName(courseName);
        model.addAttribute("trainings", trainingList);
        return "training";
    }
}
