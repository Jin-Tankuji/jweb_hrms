package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.UserRequest;
import com.nhom2.hrms.dto.response.UserResponse;
import com.nhom2.hrms.entity.User;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest req);
    UserResponse toUserResponse(User user);
}