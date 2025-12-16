package org.gamelist.gamelistapirest.Service.UserService;

import lombok.RequiredArgsConstructor;
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
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class UserServiceImpl implements UserService {

    private final UserRepository userRepository;
    private final UserMapper userMapper;

    @Override
    public UserResponseDTO createUser(UserCreationDTO request) {
        //VALIDACIONES
        if (request.getEmail() == null || request.getPassword() == null || request.getUsername() == null) {
            throw new DatosNoCorrectosException("Los campos son obligatorios");
        }
        if(userRepository.existsByEmail(request.getEmail())) {
            throw new EmailExistenteException("Email existente");
        }
        if(userRepository.existsByUsername(request.getUsername())) {
            throw new UsuarioExistenteException("Usuario existente");
        }

        //Transforma el usuario enviado a Entidad para guardarlo en BD
        User user = userMapper.toEntity(request);
        //GUARDADO
        userRepository.save(user);

        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO getUserById(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsuarioNoEncontradoException("Usuario no encontrado"));

        return userMapper.toDTO(user);
    }

    @Override
    public List<UserResponseDTO> getAllUsers() {
        return userRepository.findAll()
                .stream()
                .map(userMapper::toDTO)
                .toList();
    }

    @Override
    public UserResponseDTO getUserByUsername(String username) {
        User user = userRepository.findByUsername(username).orElseThrow(() ->
                new UsuarioNoEncontradoException("Usuario no encontrado"));

        return userMapper.toDTO(user);
    }

    @Override
    public UserResponseDTO updateUser(Long id, UserUpdateDTO request) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsuarioNoEncontradoException("Usuario no encontrado"));
        userMapper.updateEntity(request, user);
        User updatedUser = userRepository.save(user);
        return userMapper.toDTO(updatedUser);
    }

    @Override
    public void deleteUser(Long id) {
        User user = userRepository.findById(id).orElseThrow(() ->
                new UsuarioNoEncontradoException("Usuario no encontrado"));
        userRepository.delete(user);
    }
}
