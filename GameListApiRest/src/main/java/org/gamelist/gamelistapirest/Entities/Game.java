package org.gamelist.gamelistapirest.Entities;


import jakarta.persistence.*;
import lombok.*;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.security.core.userdetails.UserDetails;

import java.time.LocalDate;


/**
 * Entidad que representa a juego. Presenta gettes/setters y constructores con todos los
 * parámetros y constructor vacío.
 *
 * @author María del Carmen F.
 *
 */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
@Entity
@Table(name="games")
public class Game {
    /**
     * Id autoincremental
     */
    @Id
    //Esto equivale a tener en la BD AutoIncrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    /**ID si el juego proviene de la api externa*/
    @Column(name="external_id", unique = true)
    private Long externalId;

    /**Título del juego*/
    private String title;

    /**Descripción del juego*/
    //Define esa columna con el tipo TEXT
    @Column(columnDefinition = "TEXT")
    private String description;

    /**Fecha de salida del juego*/
    @Column(name="release_date")
//    @DateTimeFormat(fallbackPatterns = "DD-MM-YYYY")
    private LocalDate releaseDate;

    /**Desarrolladora del videojuego*/
    private String developer;

    /**Imágen del videojuego*/
    @Column(name="image_url")
    private String imageUrl;

    /**Booleana que comprueba si un juego lo ha creado un usuario*/
    @Column(name = "is_user_created", nullable = false)
    private boolean userCreated;

    /**Usuario que ha creado un videojuego*/
    @ManyToOne
    @JoinColumn(name = "created_by_user_id",nullable = true)
    private User createdBy;

}
