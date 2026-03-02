package org.gamelist.gamelistapirest.DTO.UserDTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class AuthResponseDTO {
    private String token;
    private String email;
    private String username;
    private String role;
}