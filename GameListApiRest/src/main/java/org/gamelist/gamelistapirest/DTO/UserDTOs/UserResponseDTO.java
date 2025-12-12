package org.gamelist.gamelistapirest.DTO.UserDTOs;

import lombok.Data;

//Nos genera setters,getters,equals...
@Data
public class UserResponseDTO {

    private Long id;
    private String username;
    private String email;

}
