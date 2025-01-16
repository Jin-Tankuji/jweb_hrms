package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.LeaveRequest;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.entity.Leave;
import com.nhom2.hrms.mapper.LeaveMapper;
import com.nhom2.hrms.repository.EmpRepository;
import com.nhom2.hrms.repository.LeaveRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
public class LeaveService {
    LeaveRepository leaveRepository;
    LeaveMapper leaveMapper;
    EmpRepository empRepository;

    public List<String> getAllEmployeeId() {
        return empRepository.findAll().stream()
                .map(Employee::getEmployeeId)
                .collect(Collectors.toList());
    }

    // Creates a new Leave on the request and saves it to the database
    public Leave createLeave(LeaveRequest req) {
        Leave leave = leaveMapper.toLeave(req);

        Employee emp = new Employee();
        emp.setEmployeeId(req.getEmployeeId());
        leave.setEmployee(emp);

        return leaveRepository.save(leave);
    }

    // Retrieves all Leaves from the database
    public List<Leave> getLeaves() {
        return leaveRepository.findAll();
    }

    // Retrieves a specific Leave by its ID, throws an exception if not found
    public Leave getLeave(String id) {
        return leaveRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy thông tin nghỉ phép"));
    }

    // Updates an existing Leave on the provided request and ID
    public Leave updateLeave(LeaveRequest req, String id) {
        Leave leave = getLeave(id);

        Employee emp = new Employee();
        emp.setEmployeeId(req.getEmployeeId());
        leave.setEmployee(emp);

        leaveMapper.updateLeave(req, leave);

        return leaveRepository.save(leave);
    }

    // Deletes a Leave by its ID
    public void deleteLeave(String id) {
        leaveRepository.deleteById(id);
    }
}
