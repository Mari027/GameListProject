package org.gamelist.gamelistapirest.Service.UserService;

import org.gamelist.gamelistapirest.DTO.UserDTOs.UserCreationDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserUpdateDTO;

public interface UserService {

    UserResponseDTO createUser(UserCreationDTO user);
    UserResponseDTO updateUser(UserUpdateDTO user);
    void deleteUser(Long userId);

    UserResponseDTO getUser(Long userId);


}
