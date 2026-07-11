package com.fundoonotes.fundoo_notes.mapper;

import com.fundoonotes.fundoo_notes.dto.UserDTO;
import com.fundoonotes.fundoo_notes.model.User;
import lombok.RequiredArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UserMapper {

    private final ModelMapper modelMapper;   // final = constructor injection now works

    public User toEntity(UserDTO dto) {
        return modelMapper.map(dto, User.class);
    }
}