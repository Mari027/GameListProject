package org.gamelist.gamelistapirest.DTO.GamesDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

/**
 * Este DTO nos ayuda a la hora de mostrar la lista de juegos
 * Reduce el JSON y mejora el rendimiento
 * */
@Data
@AllArgsConstructor
public class GameSummaryDTO {
    private Long id;
    private String title;
    private String coverImageUrl;
    private Long externalId;
}
