package org.gamelist.gamelistapirest.Service.UserService;

import org.gamelist.gamelistapirest.DTO.UserDTOs.UserCreationDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserUpdateDTO;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Exceptions.DatosNoCorrectosException;
import org.gamelist.gamelistapirest.Exceptions.EmailExistenteException;
import org.gamelist.gamelistapirest.Exceptions.UsuarioExistenteException;
import org.gamelist.gamelistapirest.Exceptions.UsuarioNoEncontradoException;
import org.gamelist.gamelistapirest.Mapper.UserMapper;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class UserServiceImplTest {

    @Mock
    private UserRepository userRepository;

    @Mock
    private UserMapper userMapper;

    @InjectMocks
    private UserServiceImpl userService;


    @Test
    void createUser_DatosNoCorrectosException() {
        UserCreationDTO request = new UserCreationDTO();
        request.setEmail(null);
        request.setPassword("1234");
        request.setNickname("test");

        assertThrows(DatosNoCorrectosException.class, () -> userService.createUser(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_EmailExistenteException() {
        UserCreationDTO request = new UserCreationDTO();
        request.setEmail("test@gmail.com");
        request.setPassword("1234");
        request.setNickname("test");

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(true);

        assertThrows(EmailExistenteException.class, () -> userService.createUser(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_UsuarioExistenteException() {
        UserCreationDTO request = new UserCreationDTO();
        request.setEmail("test@gmail.com");
        request.setPassword("1234");
        request.setNickname("existente");

        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(userRepository.existsByNickname("existente")).thenReturn(true);

        assertThrows(UsuarioExistenteException.class, () -> userService.createUser(request));
        verify(userRepository, never()).save(any());
    }

    @Test
    void createUser_ok() {
        //Creamos la request para crear el usuario con todos los datos correctos
        UserCreationDTO request = new UserCreationDTO();
        request.setEmail("test@gmail.com");
        request.setPassword("1234");
        request.setNickname("test");

        //Creamos el usuario
        User user = User.builder().email("test@gmail.com").nickname("test").build();
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "test", "test@gmail.com");

        //Comprobamos condiciones
        when(userRepository.existsByEmail("test@gmail.com")).thenReturn(false);
        when(userRepository.existsByNickname("test")).thenReturn(false);
        //Mapeamos
        when(userMapper.toEntity(request)).thenReturn(user);
        when(userMapper.toDTO(user)).thenReturn(responseDTO);

        //Llamamos al servicio
        UserResponseDTO result = userService.createUser(request);

        //Verificamos no nulos y comparamos el resultado con lo que debe tener
        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
        verify(userRepository).save(user);
    }


    @Test
    void getUserById_UsuarioNoEncontradoException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class, () -> userService.getUserById(1L));
    }

    @Test
    void getUserById_ok() {
        //Creamos el usuario
        User user = User.builder().email("test@gmail.com").nickname("test").build();
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "test", "test@gmail.com");

        //Lo buscamos y mapeamos
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(responseDTO);

        UserResponseDTO result = userService.getUserById(1L);

        //verificamos no nulos y que coincidan los datos
        assertNotNull(result);
        assertEquals("test@gmail.com", result.getEmail());
    }


    @Test
    void getAllUsers_ok() {
        //Creamos usuarios
        User user1 = User.builder().email("a@gmail.com").nickname("a").build();
        User user2 = User.builder().email("b@gmail.com").nickname("b").build();

        //Buscamos y mapeamos
        when(userRepository.findAll()).thenReturn(List.of(user1, user2));
        when(userMapper.toDTO(user1)).thenReturn(new UserResponseDTO(1L, "a", "a@gmail.com"));
        when(userMapper.toDTO(user2)).thenReturn(new UserResponseDTO(2L, "b", "b@gmail.com"));

        //Llamamos al servicio
        List<UserResponseDTO> result = userService.getAllUsers();

        //verificamos respuesta
        assertEquals(2, result.size());
    }

    @Test
    void getUserByNickname_UsuarioNoEncontradoException() {
        when(userRepository.findByNickname("usuario")).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class, () -> userService.getUserByNickname("usuario"));
    }

    @Test
    void getUserByNickname_ok() {
        //Creamos usuario
        User user = User.builder().email("test@gmail.com").nickname("test").build();
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "test", "test@gmail.com");

        //Buscamos y mapeamos
        when(userRepository.findByNickname("test")).thenReturn(Optional.of(user));
        when(userMapper.toDTO(user)).thenReturn(responseDTO);

        //LLamamos al servicio
        UserResponseDTO result = userService.getUserByNickname("test");

        //Verifica no nulos y el contenido del resultado
        assertNotNull(result);
        assertEquals("test", result.getNickname());
    }


    @Test
    void updateUser_UsuarioNoEncontradoException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> userService.updateUser(1L, new UserUpdateDTO()));
    }

    @Test
    void updateUser_ok() {
        //Creamos el usuario y el dto de actualización
        User user = User.builder().email("test@gmail.com").nickname("test").build();
        UserUpdateDTO updateDTO = new UserUpdateDTO();
        UserResponseDTO responseDTO = new UserResponseDTO(1L, "test", "test@gmail.com");

        //Buscamos y guardamos la actualización
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userRepository.save(user)).thenReturn(user);
        //Mapeamos
        when(userMapper.toDTO(user)).thenReturn(responseDTO);

        //Llamamos al servicio
        UserResponseDTO result = userService.updateUser(1L, updateDTO);

        //Verificamos no nulos, mapeos e interacción con BD
        assertNotNull(result);
        verify(userMapper).updateEntity(updateDTO, user);
        verify(userRepository).save(user);
    }


    @Test
    void deleteUser_UsuarioNoEncontradoException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class, () -> userService.deleteUser(1L));
        verify(userRepository, never()).delete(any());
    }

    @Test
    void deleteUser_ok() {
        User user = User.builder().email("test@gmail.com").nickname("test").build();

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));

        userService.deleteUser(1L);

        verify(userRepository).delete(user);
    }
}