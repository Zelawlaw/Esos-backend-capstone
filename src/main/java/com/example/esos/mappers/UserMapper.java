package com.example.esos.mappers;

import com.example.esos.dto.UserResponse;
import com.example.esos.entities.User;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {
    @Mapping(source = "id", target = "id")
    @Mapping(source = "username", target = "username")
    UserResponse userToUserResponse(User user);

}
