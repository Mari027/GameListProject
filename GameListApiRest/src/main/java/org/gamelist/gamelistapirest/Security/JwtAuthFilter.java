package org.gamelist.gamelistapirest.Security;

import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.web.authentication.WebAuthenticationDetailsSource;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;

@Component
@RequiredArgsConstructor
/**
 * Intercepta peticiones, las lee, las valida y nos dice que usuario es
 * */
public class JwtAuthFilter extends OncePerRequestFilter {

    private final JwtService jwtService;
    private final UserDetailsServiceImpl userDetailsService;

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain) throws ServletException, IOException {

        //Leemos el header Authorization de la petición
        String authHeader = request.getHeader("Authorization");

        //Si no hay header o no empieza por "Bearer ", dejamos pasar sin autenticar
        if (authHeader == null || !authHeader.startsWith("Bearer ")) {
            filterChain.doFilter(request, response);
            return;
        }

        //Extraemos el token quitando "Bearer " del principio
        String token = authHeader.substring(7);

        //Extraemos el email del token
        String email = jwtService.extractEmail(token);

        //Si hay email y el usuario aún no está autenticado en esta petición
        if (email != null && SecurityContextHolder.getContext().getAuthentication() == null) {

            //Cargamos el usuario de la BD
            UserDetails userDetails = userDetailsService.loadUserByUsername(email);

            //Validamos que el token sea correcto para ese usuario
            if (jwtService.isTokenValid(token, userDetails)) {

                //Creamos el objeto de autenticación y lo metemos en el contexto de Spring Security
                UsernamePasswordAuthenticationToken authToken =
                        new UsernamePasswordAuthenticationToken(
                                userDetails,
                                null,
                                userDetails.getAuthorities()
                        );
                authToken.setDetails(new WebAuthenticationDetailsSource().buildDetails(request));
                SecurityContextHolder.getContext().setAuthentication(authToken);
            }
        }

        //Continuamos con el siguiente filtro de la cadena
        filterChain.doFilter(request, response);
    }
}