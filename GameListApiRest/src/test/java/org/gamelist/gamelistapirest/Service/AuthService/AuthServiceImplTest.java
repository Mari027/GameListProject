package org.gamelist.gamelistapirest.Service.AuthService;


import org.gamelist.gamelistapirest.DTO.UserDTOs.Auth.AuthResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.Auth.LoginRequestDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.Auth.RegisterRequestDTO;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Exceptions.EmailExistenteException;
import org.gamelist.gamelistapirest.Exceptions.UsuarioExistenteException;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.gamelist.gamelistapirest.Security.JwtService;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.password.PasswordEncoder;

import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class AuthServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private PasswordEncoder passwordEncoder;

    @Mock
    private JwtService jwtService;

    @Mock
    private AuthenticationManager authenticationManager;

    @InjectMocks
    private AuthService authService;


    /**
     * Test para comprobar la duplicidad de usuario
     * al intentar registrarse con un email ya registrado en la bd
     * */
    @Test
    void register_EmailExistenteException() {

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setEmail("test@gmail.com");
        request.setNickname("testuser");
        request.setPassword("1234");

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        //Lanza la excepción cuando nos intentamos registrar
        assertThrows(EmailExistenteException.class, () -> authService.register(request));

        // Comprobamos que no se guarda nunca en BD
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_UsuarioExistenteException() {

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setEmail("test@gmail.com");
        request.setNickname("testuser");
        request.setPassword("1234");

        when(userRepository.existsByNickname("testuser")).thenReturn(true);

        //Lanza la excepción cuando nos intentamos registrar
        assertThrows(UsuarioExistenteException.class, () -> authService.register(request));

        // Comprobamos que no se guarda nunca en BD
        verify(userRepository, never()).save(any());
    }

    @Test
    void register_ok_DevuelveToken() {

        RegisterRequestDTO request = new RegisterRequestDTO();
        request.setEmail("nuevo@gmail.com");
        request.setNickname("nuevo");
        request.setPassword("1234");

        when(userRepository.existsByEmail("nuevo@gmail.com")).thenReturn(false);
        when(userRepository.existsByNickname("nuevo")).thenReturn(false);
        when(passwordEncoder.encode("1234")).thenReturn("hashed1234");
        when(jwtService.generateToken(any())).thenReturn("token123");


        AuthResponseDTO response = authService.register(request);

        //Comprobamos que los datos introducidos en el registro, coinciden con la respuesta devuelta
        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals("nuevo@gmail.com", response.getEmail());
        assertEquals("nuevo", response.getNickname());
        assertEquals("USER", response.getRole());

        // Comprobamos que el usuario se guarda en BD
        verify(userRepository).save(any(User.class));
    }

    @Test
    void login_ok_DevuelveToken() {

        LoginRequestDTO request = new LoginRequestDTO();
        request.setEmail("test@gmail.com");
        request.setPassword("1234");
        //Construimos un usuario con los mismos datos
        User user = User.builder()
                .email("test@gmail.com")
                .nickname("testuser")
                .password("hashed1234")
                .role("USER")
                .build();

        when(userRepository.findByEmail("test@gmail.com")).thenReturn(Optional.of(user));
        when(jwtService.generateToken(user)).thenReturn("token123");

        //Hacemos login
        AuthResponseDTO response = authService.login(request);

        //Comprobamos el token del usuario logueado
        assertNotNull(response);
        assertEquals("token123", response.getToken());
        assertEquals("test@gmail.com", response.getEmail());

        //Comprobamos autenticación con Spring Security
        verify(authenticationManager).authenticate(any(UsernamePasswordAuthenticationToken.class));
    }
}
