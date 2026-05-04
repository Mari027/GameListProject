package org.gamelist.gamelistapirest.DTO.GamesDTOs;

import lombok.Data;

import java.time.LocalDate;

//Nos genera setters,getters,equals...
@Data
/**
 * DTO de creación de un juego personalizado
 *
 * @author María del Carmen F.
 * */
public class CustomGameCreationDTO {
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String developer;
    private String imageUrl;
}
