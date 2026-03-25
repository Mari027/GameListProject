package org.gamelist.gamelistapirest.Service.UserService;

import org.gamelist.gamelistapirest.DTO.UserDTOs.UserCreationDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserUpdateDTO;

import java.util.List;

public interface UserService {

    // CREATE
    UserResponseDTO createUser(UserCreationDTO request);

    // READ
    UserResponseDTO getUserById(Long id);
    List<UserResponseDTO> getAllUsers();
    UserResponseDTO getUserByNickname(String nickname);

    // UPDATE
    UserResponseDTO updateUser(Long id, UserUpdateDTO request);

    // DELETE
    void deleteUser(Long id);


}
