package org.gamelist.gamelistapirest.Entities;


import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDate;

@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
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
    private LocalDate releaseDate;

    private String developer;

    @Column(name="image_url")
    private String imageUrl;

    //Es por si el juego lo ha añadido el usuario manualmente
    @Column(name = "is_custom", nullable = false)
    private boolean custom;

    @ManyToOne
    @JoinColumn(name = "created_by_user_id",nullable = true)
    private User createdBy;

}
