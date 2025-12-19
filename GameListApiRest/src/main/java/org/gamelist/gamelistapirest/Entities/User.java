package org.gamelist.gamelistapirest.Entities;

import jakarta.persistence.*;
import jakarta.validation.constraints.*;
import lombok.*;


import java.time.LocalDateTime;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User {
    @Id
    //Esto equivale a tener en la BD AutoIncrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(unique = true)
    private String username;


    @Column(unique = true)
    private String email;

    private String password;

    @Column(name = "image_url")
    private String imageUrl;

    @Column(name = "created_at",updatable = false)
    private LocalDateTime createdAt;

    //Esta anotacion ejecuta este metodo ANTES de guardar la entidad
    //por primera vez en la BD
    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }

}
