package org.gamelist.gamelistapirest.DTO.UserDTOs;

import lombok.Data;

//Nos genera setters,getters,equals...
@Data
public class UserCreationDTO {
    private String username;
    private String email;
    private String password;
    private String imageUrl;
}
