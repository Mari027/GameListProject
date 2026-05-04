package org.gamelist.gamelistapirest.DTO.ExternalGameDTOs;

import lombok.Data;

@Data
/**
 * DTO de respuesta reducido para los juegos de la Api Externa Rawg.io para mejorar el rendimiento del backend
 * al solicitar una lista completa de estos
 *
 * @author María del Carmen F.
 * */
public class ExternalGameSummaryDTO {
    private Long id;
    private String name;
    private String description_raw;
    private String backgroundImage;
}
