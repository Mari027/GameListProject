package org.gamelist.gamelistapirest.DTO.UserGamesDTOs;

import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.*;
import lombok.Data;
import org.gamelist.gamelistapirest.Enums.GameStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

@Data
/**
 * DTO con los parámetros necesarios para añadir un juego en la lista de un usuario
 *
 * @author María del Carmen F.
 * */
public class UserGamesRequestDTO {
    private Long gameId; //Juegos internos, creados por usuarios
    private Long externalGameId; //Juegos de la api externas añadidos por algún usuario

    @NotNull(message = "El estado del juego es obligatorio")
    private GameStatus gameStatus;

    @Min(value = 0, message = "La valoración no puede ser negativa")
    @Max(value = 5, message = "La valoración no puede ser mayor que 5")
    private Integer rating;

    @PositiveOrZero(message = "Las horas jugadas no pueden ser negativas")
    private Double hoursPlayed;

    @PastOrPresent(message = "La fecha de inicio no puede ser futura")
    private LocalDate startedAt;

    @PastOrPresent(message = "La fecha de finalización no puede ser futura")
    private LocalDate completedAt;

    @Size(max = 1000, message = "La reseña no puede superar los 1000 caracteres")
    private String review;
}
