package org.gamelist.gamelistapirest.Service.AuthService;

import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.UserDTOs.Auth.AuthResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.Auth.LoginRequestDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.Auth.RegisterRequestDTO;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Exceptions.EmailExistenteException;
import org.gamelist.gamelistapirest.Exceptions.UsuarioExistenteException;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.gamelist.gamelistapirest.Security.JwtService;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtService jwtService;
    private final AuthenticationManager authenticationManager;

    public AuthResponseDTO register(RegisterRequestDTO request) {

        // Comprobamos que no exista el email o username
        if (userRepository.existsByEmail(request.getEmail())) {
            throw new EmailExistenteException("Email ya registrado");
        }
        if (userRepository.existsByNickname(request.getNickname())) {
            throw new UsuarioExistenteException("Nickname ya en uso");
        }

        // Construimos el usuario hasheando la contraseña
        User user = User.builder()
                .nickname(request.getNickname())
                .email(request.getEmail())
                .password(passwordEncoder.encode(request.getPassword()))
                .role("USER") //TODOS los usuarios creados desde la app serán usuarios normales
                .build();

        userRepository.save(user);

        // Generamos el token y devolvemos el usuario con su token
        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token, user.getEmail(), user.getNickname(), user.getRole());
    }

    public AuthResponseDTO login(LoginRequestDTO request) {

        // Spring Security verifica email + contraseña automáticamente
        // Si son incorrectos lanza una excepción
        authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        request.getEmail(),
                        request.getPassword()
                )
        );

        // Si llega aquí, las credenciales son correctas y genera/devuelve el token
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow();

        String token = jwtService.generateToken(user);
        return new AuthResponseDTO(token, user.getEmail(), user.getNickname(), user.getRole());
    }
}