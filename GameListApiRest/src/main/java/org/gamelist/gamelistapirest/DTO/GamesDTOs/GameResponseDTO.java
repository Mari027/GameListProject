package org.gamelist.gamelistapirest.DTO.GamesDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

import java.time.LocalDate;
//Nos genera setters,getters,equals...
@Data
@AllArgsConstructor
/**
 * DTO de respuesta para la entidad de Game
 *
 * @author María del Carmen F.
 * */
public class GameResponseDTO {

    private Long id;
    private String title;
    private String description;
    private LocalDate releaseDate;
    private String developer;
    private String imageUrl;
    private Boolean isCustom;
}
