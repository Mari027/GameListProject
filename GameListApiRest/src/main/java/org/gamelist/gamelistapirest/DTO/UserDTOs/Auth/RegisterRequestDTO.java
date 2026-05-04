package org.gamelist.gamelistapirest.DTO.UserDTOs.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * DTO para el registro de usuario con los parámetros necesarios
 *
 * @author María del Carmen F.
 * */
public class RegisterRequestDTO {
    private String nickname;
    private String email;
    private String password;
}