package org.gamelist.gamelistapirest.DTO.UserDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

//Nos genera setters,getters,equals...
@Data
@AllArgsConstructor
/**
 * DTO de respuesta con los datos del usuario
 *
 * @author María del Carmen F.
 * */
public class UserResponseDTO {

    private Long id;
    private String nickname;
    private String email;

}
