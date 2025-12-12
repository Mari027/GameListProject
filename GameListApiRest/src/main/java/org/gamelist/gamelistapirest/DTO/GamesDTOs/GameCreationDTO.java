package org.gamelist.gamelistapirest.DTO.GamesDTOs;

import lombok.Data;

import java.time.LocalDate;

//Nos genera setters,getters,equals...
@Data
public class GameCreationDTO {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String developer;
    private String imageUrl;
}
