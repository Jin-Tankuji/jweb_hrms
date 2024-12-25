package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.AttdRequest;
import com.nhom2.hrms.entity.Attendance;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.mapper.AttdMapper;
import com.nhom2.hrms.repository.AttdRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class AttdService {
    AttdRepository attdRepository;
    AttdMapper attdMapper;

    // Creates a new attendance on the request and saves it to the database
    public Attendance createAttendance(AttdRequest req) {
        Attendance attd = attdMapper.toAttendance(req);

        Employee emp = new Employee();
        emp.setEmployeeId(req.getEmployee().getEmployeeId());
        attd.setEmployee(emp);

        return attdRepository.save(attd);
    }

    // Retrieves all attendances from the database
    public List<Attendance> getAttendances() {
        return attdRepository.findAll();
    }

    // Retrieves a specific attendance by its ID, throws an exception if not found
    public Attendance getAttendance(String id) {
        return attdRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy bảng chấm công"));
    }

    public List<Attendance> getAttendanceByEmployeeId(String employeeId) {
        return attdRepository.findByEmployeeEmployeeId(employeeId);
    }

    // Updates an existing attendance on the provided request and ID
    public Attendance updateAttendance(AttdRequest req, String id) {
        Attendance attd = getAttendance(id);

        Employee emp = new Employee();
        emp.setEmployeeId(req.getEmployee().getEmployeeId());
        attd.setEmployee(emp);
        attdMapper.updateAttendance(req, attd);

        return attdRepository.save(attd);
    }

    // Deletes a attendance by its ID
    public void deleteAttendance(String id) {
        attdRepository.deleteById(id);
    }
}