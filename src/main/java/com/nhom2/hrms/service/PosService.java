package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.PosRequest;
import com.nhom2.hrms.entity.Position;
import com.nhom2.hrms.mapper.PosMapper;
import com.nhom2.hrms.repository.PosRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class PosService {
    PosRepository posRepository;
    PosMapper posMapper;

    // Creates a new position on the request and saves it to the database
    public Position createPosition(PosRequest req) {
        Position pos = posMapper.toPosition(req);
        return posRepository.save(pos);
    }

    // Retrieves all positions from the database
    public List<Position> getPositions() {
        return posRepository.findAll();
    }

    // Retrieves a specific position by its ID, throws an exception if not found
    public Position getPosition(String id) {
        return posRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy vị trí"));
    }

    // Updates an existing position on the provided request and ID
    public Position updatePosition(PosRequest req, String id) {
        Position pos = getPosition(id);
        posMapper.updatePosition(req, pos);
        return posRepository.save(pos);
    }

    // Deletes a position by its ID
    public void deletePosition(String id) {
        posRepository.deleteById(id);
    }
}
