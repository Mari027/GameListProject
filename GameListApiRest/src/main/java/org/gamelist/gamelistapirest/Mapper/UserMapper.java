package org.gamelist.gamelistapirest.Mapper;


import org.gamelist.gamelistapirest.DTO.UserDTOs.UserCreationDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserUpdateDTO;
import org.gamelist.gamelistapirest.Entities.User;
import org.springframework.stereotype.Component;

@Component
/**
 * Se encarga de mapear todo lo relacionado con los usuarios
 * tanto la creación, actualización y vista de estos
 *
 * @author María del Carmen F.
 * */
public class UserMapper {

    public User toEntity(UserCreationDTO dto){
        User user = new User();
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setImageUrl(dto.getImageUrl());
        return user;
    }

    public User updateEntity(UserUpdateDTO dto, User user){
        user.setNickname(dto.getNickname());
        user.setEmail(dto.getEmail());
        user.setPassword(dto.getPassword());
        user.setImageUrl(dto.getImageUrl());
        return user;
    }

    public UserResponseDTO toDTO(User user){

        return new UserResponseDTO (
                user.getId(),
                user.getNickname(),
                user.getEmail()
        );
    }
}
