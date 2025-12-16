package org.gamelist.gamelistapirest.Entities;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.gamelist.gamelistapirest.Enums.GameStatus;

import java.time.LocalDate;
import java.time.LocalDateTime;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(
        name = "user_games",
        uniqueConstraints = @UniqueConstraint(columnNames = {"user_id", "game_id"})
)
public class UserGames {
    @Id
    //Esto equivale a tener en la BD AutoIncrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne
    @JoinColumn(name = "user_id", nullable=false)
    private User user;

    @ManyToOne
    @JoinColumn(name = "game_id")
    private Game game;

    @Enumerated(EnumType.STRING)
    @Column(nullable=false)
    private GameStatus status;

    //Se validará 1-5
    private Integer rating;

    @Column(name = "hours_played")
    private Double hoursPlayed;

    @Column(name="started_at")
    private LocalDate startedAt;

    @Column(name="completed_at")
    private LocalDate completedAt;

    //Define esa columna con el tipo TEXT
    @Column(columnDefinition = "TEXT")
    private String review;

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
