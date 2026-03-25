package org.gamelist.gamelistapirest.DTO.UserDTOs.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class RegisterRequestDTO {
    private String nickname;
    private String email;
    private String password;
}