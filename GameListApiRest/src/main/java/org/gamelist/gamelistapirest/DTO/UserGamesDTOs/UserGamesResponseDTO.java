package org.gamelist.gamelistapirest.DTO.UserGamesDTOs;

import lombok.AllArgsConstructor;
import lombok.Data;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameSummaryDTO;
import org.gamelist.gamelistapirest.Enums.GameStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;
@Data
@AllArgsConstructor
/**
 * DTO de respuesta con los datos de un juego en la lista de un usuario
 *
 * @author María del Carmen F.
 * */
public class UserGamesResponseDTO {
    private Long Id;
    private GameSummaryDTO game;
    private GameStatus gameStatus;
    private Integer rating;
    private Double hoursPlayed;
    private LocalDate startedAt;
    private LocalDate completedAt;
    private String review;
    private LocalDateTime createdAt;
}
