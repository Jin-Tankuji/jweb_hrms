package com.nhom2.hrms.dto.request;

import jakarta.validation.constraints.Size;
import lombok.*;
import lombok.experimental.FieldDefaults;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@FieldDefaults(level = AccessLevel.PRIVATE)
public class UserRequest {
    EmpRequest employee;

    @Size(min = 3, message = "Username phải nhập ít nhất 3 ký tự.")
    String username;

    @Size(min = 8, message = "Mật khẩu phải nhập ít nhất 8 ký tự.")
    String password;
}
