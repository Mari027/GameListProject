package org.gamelist.gamelistapirest.Security;

import org.gamelist.gamelistapirest.Entities.User;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

/**
 * Clase encargada de recuperar el usuario autenticado en la petición actual
 * @author Maria del Carmen F.
 */
@Component
public class AuthUtils {

    // Recogemos el usuario autenticado en la petición actual
    public User getAuthenticatedUser() {
        return (User) SecurityContextHolder.getContext()
                .getAuthentication()
                .getPrincipal();
    }
}