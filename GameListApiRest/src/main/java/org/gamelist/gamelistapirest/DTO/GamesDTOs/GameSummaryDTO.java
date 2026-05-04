package org.gamelist.gamelistapirest.DTO.GamesDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor

/**
 * DTO de respuesta de la entidad Game con menor número de parámetros
 * para mejorar el rendimiento del backend a la hora de hacer una petición completa de todos los juegos
 *
 * @author María del Carmen F.
 * */
public class GameSummaryDTO {
    private Long id;
    private String title;
    private String coverImageUrl;
    private Long externalId;
}
