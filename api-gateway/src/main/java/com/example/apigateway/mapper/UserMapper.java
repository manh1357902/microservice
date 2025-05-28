package com.example.apigateway.mapper;


import com.example.apigateway.dto.response.UserResponse;
import com.example.apigateway.entity.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;


/**
 * Mapper class for converting User entity to UserResponse DTO.
 * This class uses ModelMapper for the conversion.
 */
@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;

    /**
     * Converts a User entity to a UserResponse DTO.
     *
     * @param user the User entity to convert
     * @return the converted UserResponse DTO
     */
    public UserResponse mapEntityToResponse(User user) {
        return modelMapper.map(user, UserResponse.class);
    }
}
