package org.gamelist.gamelistapirest.DTO.UserDTOs.Auth;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
/**
 * DTO para el login con los parámetros necesarios
 *
 * @author María del Carmen F.
 * */
public class LoginRequestDTO {
    private String email;
    private String password;
}