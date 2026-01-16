package org.gamelist.gamelistapirest.Entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="games")
public class Game {
    @Id
    //Esto equivale a tener en la BD AutoIncrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String title;

    //Define esa columna con el tipo TEXT
    @Column(columnDefinition = "TEXT")
    private String description;

    @Column(name="release_date")
//    @DateTimeFormat(fallbackPatterns = "DD-MM-YYYY")
    private LocalDate releaseDate;

    private String developer;

    @Column(name="image_url")
    private String imageUrl;

    //Es por si el juego lo ha añadido el usuario manualmente
    @Column(name = "is_user_created", nullable = false)
    private boolean userCreated;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id",nullable = true)
    private User createdBy;

}
