package com.nhom2.hrms.mapper;

import com.nhom2.hrms.dto.request.UserRequest;
import com.nhom2.hrms.dto.response.UserResponse;
import com.nhom2.hrms.entity.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

@Mapper(componentModel = "spring")
public interface UserMapper {
    User toUser(UserRequest req);

    @Mapping(target = "roles", expression = "java(mapRolesToString(user))")
    UserResponse toUserResponse(User user);

    default String mapRolesToString(User user) {
        if (user.getRoles() == null || user.getRoles().isEmpty()) {
            return "";
        }
        return String.join(" ", user.getRoles());
    }
}
