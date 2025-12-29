package org.gamelist.gamelistapirest.Controller;


import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserCreationDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserUpdateDTO;
import org.gamelist.gamelistapirest.Service.UserService.UserService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/users")
public class UserController {

    private final UserService userService;

    //ENDPOINTS PARA METODOS DE SOLO LECTURA
    @GetMapping("/id/{userId}")
    public ResponseEntity<UserResponseDTO> getUser(@PathVariable Long userId) {
        UserResponseDTO user = userService.getUserById(userId);
        return ResponseEntity.ok(user);
    }
    @GetMapping()
    public ResponseEntity<List<UserResponseDTO>> getAllUsers() {
        return ResponseEntity.ok(userService.getAllUsers());
    }

    @GetMapping("/username/{username}")
    public ResponseEntity<UserResponseDTO> getUserByUsername(@PathVariable String username) {
        UserResponseDTO user = userService.getUserByUsername(username);
        return ResponseEntity.ok(user);
    }

    //ENDPOINT DE CREACIÓN
    @PostMapping
    public ResponseEntity<UserResponseDTO> createUser(@RequestBody UserCreationDTO userCreationDTO) {
        UserResponseDTO user = userService.createUser(userCreationDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(user);
    }

    //ENDPOINT DE UPDATE
    @PutMapping("/{userId}")
    public ResponseEntity<UserResponseDTO> updateUser(@RequestBody UserUpdateDTO userUpdateDTO, @PathVariable Long userId) {
        UserResponseDTO user = userService.updateUser(userId, userUpdateDTO);
        return ResponseEntity.ok(user);
    }

    //ENDPOINT DE DELETE
    @DeleteMapping("/{userId}")
    public ResponseEntity<Void> deleteUser(@PathVariable Long userId) {
        userService.deleteUser(userId);
        return ResponseEntity.noContent().build(); //Se ha borrado un elemento
    }

}
