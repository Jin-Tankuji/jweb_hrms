package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.RecmtRequest;
import com.nhom2.hrms.entity.Position;
import com.nhom2.hrms.entity.Recruitment;
import com.nhom2.hrms.mapper.RecmtMapper;
import com.nhom2.hrms.repository.RecmtRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class RecmtService {
    RecmtRepository recmtRepository;
    RecmtMapper recmtMapper;

    // Creates a new Recruitment on the request and saves it to the database
    public Recruitment createRecruitment(RecmtRequest req) {
        Recruitment recmt = recmtMapper.toRecruitment(req);

        Position pos = new Position();
        pos.setPositionId(req.getPosition().getPositionId());
        recmt.setPosition(pos);

        return recmtRepository.save(recmt);
    }

    // Retrieves all Recruitments from the database
    public List<Recruitment> getRecruitments() {
        return recmtRepository.findAll();
    }

    // Retrieves a specific Recruitment by its ID, throws an exception if not found
    public Recruitment getRecruitment(String id) {
        return recmtRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin tuyển dụng"));
    }

    // Updates an existing Recruitment on the provided request and ID
    public Recruitment updateRecruitment(RecmtRequest req, String id) {
        Recruitment recmt = getRecruitment(id);

        Position pos = new Position();
        pos.setPositionId(req.getPosition().getPositionId());
        recmt.setPosition(pos);

        recmtMapper.updateRecruitment(req, recmt);

        return recmtRepository.save(recmt);
    }

    // Deletes a Recruitment by its ID
    public void deleteRecruitment(String id) {
        recmtRepository.deleteById(id);
    }
}
