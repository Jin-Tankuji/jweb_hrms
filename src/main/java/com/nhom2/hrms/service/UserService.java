package com.nhom2.hrms.service;

import com.nhom2.hrms.dto.request.UserRequest;
import com.nhom2.hrms.dto.response.UserResponse;
import com.nhom2.hrms.entity.Employee;
import com.nhom2.hrms.entity.User;
import com.nhom2.hrms.enums.Role;
import com.nhom2.hrms.mapper.UserMapper;
import com.nhom2.hrms.repository.UserRepository;
import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;
import lombok.experimental.FieldDefaults;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.access.prepost.PostAuthorize;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
@RequiredArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE, makeFinal = true)
@Slf4j
public class UserService {
    UserRepository userRepository;
    UserMapper userMapper;
    PasswordEncoder passwordEncoder;

    // Add a new user to the database
    public User createUser(UserRequest req) {
        if (userRepository.existsByUsername(req.getUsername()))
            throw new RuntimeException("Người dùng đã tồn tại");

        User user = userMapper.toUser(req);

        // Select id from table Employee
        Employee emp = new Employee();
        emp.setEmployeeId(req.getEmployee().getEmployeeId());
        user.setEmployee(emp);

        // Encrypt password by BCrypt
        user.setPassword(passwordEncoder.encode(req.getPassword()));

        Set<String> roles = new HashSet<>();
        roles.add(Role.MEMBER.name());
        user.setRoles(roles);

        return userRepository.save(user);
    }

    public UserResponse getMyInfo() {
        var context = SecurityContextHolder.getContext();
        String name = context.getAuthentication().getName();
        System.out.println("Logged-in username: " + name);

        User user = userRepository.findByUsername(name)
                .orElseThrow(RuntimeException::new);

        return userMapper.toUserResponse(user);
    }

    // Get all users in the database
    @PreAuthorize("hasRole('ADMIN')")
    public List<UserResponse> getUsers() {
        //log.info("In method get all users");
        return userRepository.findAll().stream().map(userMapper::toUserResponse).toList();
    }

    // Get user by userId in the database
    @PostAuthorize("returnObject.username == authentication.name")
    public User getUser(String id) {
        //log.info("In method get User by Id");
        return userRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Không tìm thấy người dùng"));
    }

    // Update user by userId with column password
    @PostAuthorize("returnObject.username == authentication.name")
    public User updateUser(String userId, String newPassword) {
        User user = getUser(userId);
        user.setPassword(passwordEncoder.encode(newPassword));
        return userRepository.save(user);
    }

    // Delete user by userId in the database
    @PreAuthorize("hasRole('ADMIN')")
    public void deleteUser(String id) {
        userRepository.deleteById(id);
    }

      // Add role after have user
//    @PutMapping("/users/{userId}/roles")
//    public ResponseEntity<?> addRole(@PathVariable String userId, @RequestParam String role) {
//        Optional<User> userOptional = userRepository.findById(userId);
//        if (userOptional.isPresent()) {
//            User user = userOptional.get();
//            user.getRoles().add(role);
//            userRepository.save(user);
//            return ResponseEntity.ok().build();
//        }
//        return ResponseEntity.notFound().build();
//    }
}
