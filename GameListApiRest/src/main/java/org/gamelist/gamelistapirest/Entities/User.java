package org.gamelist.gamelistapirest.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;


@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    @Id
    //Esto equivale a tener en la BD AutoIncrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nickname" ,unique = true)
    private String nickname;


    @Column(unique = true)
    private String email;

    private String password;

    private String role;

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

    // Devuelve el rol/roles del usuario
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }
    //Obtiene el identificador que sea necesario para autenticar al usuario
    //En este caso, el email que debe ser único
    public String getUsername() {
        return email;
    }

}
