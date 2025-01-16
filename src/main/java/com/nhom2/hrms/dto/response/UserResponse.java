package com.nhom2.hrms.dto.response;

import com.nhom2.hrms.dto.request.EmpRequest;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserResponse {
    String id;
    EmpRequest employee;
    String username;
    String role;
}
