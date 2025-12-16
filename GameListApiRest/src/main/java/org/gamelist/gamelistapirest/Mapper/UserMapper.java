package org.gamelist.gamelistapirest.Mapper;


import org.gamelist.gamelistapirest.DTO.UserDTOs.UserCreationDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserUpdateDTO;
import org.gamelist.gamelistapirest.Entities.User;
import org.springframework.stereotype.Component;

/**
 * Clase para pasar datos a las entidades
 * y sacar datos de las mismas
 * Mejor separacion de responsabilidades
 * */
@Component
public class UserMapper {

    public User toEntity(UserCreationDTO dto){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setImageUrl(dto.getImageUrl());
        return user;
    }

    public User updateEntity(UserUpdateDTO dto, User user){
        User user = new User();
        user.setUsername(dto.getUsername());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setImageUrl(dto.getImageUrl());
        return user;
    }

    public UserResponseDTO toDTO(User user){

        return new UserResponseDTO (
                user.getId(),
                user.getUsername(),
                user.getEmail()
        );
    }
}
