package org.gamelist.gamelistapirest.DTO.UserDTOs.Auth;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
/**
 * DTO de con los parámteros necesarios para la autenticación de usuario
 *
 * @author María del Carmen F.
 * */
public class AuthResponseDTO {
    private String token;
    private String email;
    private String nickname;
    private String role;
}