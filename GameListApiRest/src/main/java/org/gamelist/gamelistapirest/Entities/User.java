package org.gamelist.gamelistapirest.Entities;

import jakarta.persistence.*;
import lombok.*;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;


import java.time.LocalDateTime;
import java.util.Collection;
import java.util.List;

/**
 * Entidad que representa al usuario. Presenta gettes/setters y constructores con todos los
 * parámetros y vacío.
 * <p>
 * Implementa {@link UserDetails} para el uso de los métodos
 * {@code getAuthorities()} y {@code getUsername()} relacionados con la implemetación de JWT
 * en el proyecto.
 *
 * @author Maria del Carmen Farfán Gavilán
 *
 */
@Setter
@Getter
@AllArgsConstructor
@NoArgsConstructor
@Builder
@Entity
@Table(name = "users")
public class User implements UserDetails {
    /**
     * Id de usuario autoincremental
     */
    @Id
    //Esto equivale a tener en la BD AutoIncrement
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    /**
     * Nombre de usuario
     */
    @Column(name = "nickname", unique = true)
    private String nickname;
    /**
     * Email del usuario y manera de identificar a cada usuario con JWT
     */
    @Column(unique = true)
    private String email;
    /**
     * Contraseña del usuario
     */
    private String password;
    /**
     * Rol del usuario (ADMIN/USER)
     */
    private String role;
    /**
     * Imagen de usuario
     */
    @Column(name = "image_url")
    private String imageUrl;
    /**
     * Fecha de creación, insertada automáticamente al registrar un usuario
     */
    @Column(name = "created_at", updatable = false)
    private LocalDateTime createdAt;

    /**
     * Método encargado de registrar la fecha antes de guardar
     * un usuario en BD
     *
     */
    @PrePersist
    private void onCreate() {
        this.createdAt = LocalDateTime.now();
    }


    /**
     * Implementación del método {@code getAuthorities()}.
     * Devuelve una colección con los roles asociados al usuario.
     *
     * @return lista de autoridades (roles) del usuario
     */
    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        return List.of(new SimpleGrantedAuthority("ROLE_" + role));
    }

    /**
     * Implementación del método {@code getUsername()}.
     * Devuelve el email del usuario, para tener una manera de autenticar a los usuarios sin duplicidades.
     *
     * @return email del usuario
     */
    public String getUsername() {
        return email;
    }

}
