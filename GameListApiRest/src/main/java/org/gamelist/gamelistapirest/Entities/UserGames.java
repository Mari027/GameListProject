package org.gamelist.gamelistapirest.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.gamelist.gamelistapirest.Enums.GameStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;

/**
 * Entidad que representa la biblioteca de usuario. Presenta gettes/setters y constructores con todos los
 * parámetros y constructor vacío.
 *
 * @author María del Carmen F.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Builder
@Entity
@Table(
        name = "user_games",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "game_id"})
)
public class UserGames {

    /**Id autoincremental*/
    @Id
    //Esto equivale a tener en la BD AutoIncrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**Usuario al que pertenece la biblioteca*/
    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    /**Juego añadido a la biblioteca*/
    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    /**Estado del juego*/
    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private GameStatus status;

    /**Puntuación del juego*/
    private Integer rating;

    /**Horas juegadas al juego*/
    @Column(name = "hours_played")
    private Double hoursPlayed;

    /**Fecha de comienzo de jugar al juego*/
    @Column(name="started_at")
    private LocalDate startedAt;
    /**Fecha de completado el juego*/
    @Column(name="completed_at")
    private LocalDate completedAt;

    /**Reseña del juego*/
    @Column(columnDefinition = "TEXT")
    private String review;

    /**Fecha de creación de userGame*/
    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    /**
     * Esta anotacion ejecuta este metodo ANTES de guardar la entidad
     * por primera vez en la BD
     * */
    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

    /**
    * Metodo que comprueba si se ha completado un juego para actualizar
    * la fecha de completado
    * PreUpdate se ejecuta justo antes de actualizar una entidad
    * */
    @PreUpdate
    protected void onUpdate() {
        if (status == GameStatus.COMPLETED && completedAt == null) {
            completedAt = LocalDate.now();
        }
    }

}
