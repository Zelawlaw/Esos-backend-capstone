package com.example.esos.mappers;

import com.example.esos.dto.UserResponse;
import com.example.esos.entities.User;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring")
public interface UserMapper {

    UserResponse userToUserResponse(User user);

}
