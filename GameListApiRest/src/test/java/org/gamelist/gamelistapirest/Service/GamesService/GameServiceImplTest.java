package org.gamelist.gamelistapirest.Service.GamesService;

import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameCreationDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameUpdateDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.Entities.Game;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Exceptions.DatosNoCorrectosException;
import org.gamelist.gamelistapirest.Exceptions.JuegoNoEncontradoException;
import org.gamelist.gamelistapirest.Exceptions.UsuarioNoEncontradoException;
import org.gamelist.gamelistapirest.Mapper.GameMapper;
import org.gamelist.gamelistapirest.Repository.GamesRepository;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class GameServiceImplTest {

    @Mock
    private GamesRepository gamesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private GameServiceImpl gameService;



    @Test
    void createCustomGame_DatosNoCorrectosException() {

        CustomGameCreationDTO dto = new CustomGameCreationDTO();
        dto.setTitle(null);

        assertThrows(DatosNoCorrectosException.class,
                () -> gameService.createCustomGame(dto, 1L));
        verify(gamesRepository, never()).save(any());
    }

    @Test
    void createCustomGame_UsuarioNoEncontradoException() {

        CustomGameCreationDTO dto = new CustomGameCreationDTO();
        dto.setTitle("Titulo");

        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> gameService.createCustomGame(dto, 1L));
        verify(gamesRepository, never()).save(any());
    }

    @Test
    void createCustomGame_DatosCorrectos_ok() {
        //Creamos el dto del juego custom
        CustomGameCreationDTO dto = new CustomGameCreationDTO();
        dto.setTitle("Titulo");

        //Creamos el usuario y el juego
        User user = User.builder().email("test@gmail.com").build();
        Game game = new Game();
        game.setTitle("Titulo");
        GameResponseDTO responseDTO = new GameResponseDTO(null,null,null,null,null,null,null);

        //Buscamos usuario, mapeamos el juego custom del usuario
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameMapper.toEntity(dto, user)).thenReturn(game);
        //Retornamos el juego
        when(gamesRepository.save(game)).thenReturn(game);
        //Y lo mapeamos a respuesta
        when(gameMapper.toResponseDTO(game)).thenReturn(responseDTO);

        //Creamos el juego custom
        GameResponseDTO result = gameService.createCustomGame(dto, 1L);

        //Comprobamos no nulos e interacciones con BD
        assertNotNull(result);
        verify(gamesRepository).save(game);
    }


    @Test
    void getGameById_JuegoNoEncontradoException() {
        when(gamesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(JuegoNoEncontradoException.class, () -> gameService.getGameById(1L));
    }

    @Test
    void getGameById_ok() {
        Game game = new Game();
        GameResponseDTO responseDTO = new GameResponseDTO(null,null,null,null,null,null,null);

        //Busca juego y devuelve el dto
        when(gamesRepository.findById(1L)).thenReturn(Optional.of(game));
        when(gameMapper.toResponseDTO(game)).thenReturn(responseDTO);

        //Buscamos el juego
        GameResponseDTO result = gameService.getGameById(1L);

        //Verificamos no nulos
        assertNotNull(result);
    }

    @Test
    void getAllGames_SinFiltros_ok() {
        Game game1 = new Game();
        game1.setTitle("Titulo 1");
        Game game2 = new Game();
        game2.setTitle("Titulo 2");

        //Rescata la lista y los mapea
        when(gamesRepository.findAll()).thenReturn(List.of(game1, game2));
        when(gameMapper.toResponseDTO(any())).thenReturn(new GameResponseDTO(null,null,null,null,null,null,null));

        //Guardamos la lista mapeada
        List<GameResponseDTO> result = gameService.getAllGames(null, null, null);

        //Verificamos que el resultado es el esperado
        assertEquals(2, result.size());
    }

    @Test
    void getAllGames_FiltroTitulo_ok() {
        Game game1 = new Game();
        game1.setTitle("Titulo");
        Game game2 = new Game();
        game2.setTitle("Juego");

        when(gamesRepository.findAll()).thenReturn(List.of(game1, game2));
        when(gameMapper.toResponseDTO(game1)).thenReturn(new GameResponseDTO(null,null,null,null,null,null,null));

        List<GameResponseDTO> result = gameService.getAllGames("Titulo", null, null);

        //Verificamos que devuelva la lista de juegos filtrado
        assertEquals(1, result.size());
    }

    @Test
    void getAllGames_FiltroFecha_ok() {
        //Creamos una fecha para filtrar
        LocalDate fecha = LocalDate.of(2011, 11, 11);
        Game game1 = new Game();
        game1.setTitle("Titulo 1");
        game1.setReleaseDate(fecha);
        Game game2 = new Game();
        game2.setTitle("Titulo 2");
        game2.setReleaseDate(LocalDate.of(2007, 10, 10));

        when(gamesRepository.findAll()).thenReturn(List.of(game1, game2));
        when(gameMapper.toResponseDTO(game1)).thenReturn(new GameResponseDTO(null,null,null,null,null,null,null));

        //lista filtrada por fecha
        List<GameResponseDTO> result = gameService.getAllGames(null, fecha, null);

        //Verificamos que devulva 1
        assertEquals(1, result.size());
    }

    @Test
    void getAllGames_FiltroDeveloper_ok() {
        Game game1 = new Game();
        game1.setTitle("Titulo 1");
        game1.setDeveloper("Square Enix");
        Game game2 = new Game();
        game2.setTitle("Titulo 2");
        game2.setDeveloper("Nintendo");

        when(gamesRepository.findAll()).thenReturn(List.of(game1, game2));
        when(gameMapper.toResponseDTO(any())).thenReturn(new GameResponseDTO(null,null,null,null,null,null,null));

        //lista filtrada por developer
        List<GameResponseDTO> result = gameService.getAllGames(null, null, "Nintendo");

        //Verificamos el resultado devuelto
        assertEquals(1, result.size());
    }


    @Test
    void updateGame_JuegoNoEncontradoException() {
        when(gamesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(JuegoNoEncontradoException.class,
                () -> gameService.updateGame(1L, new CustomGameUpdateDTO()));
    }

    @Test
    void updateGame_ok() {
        //Creamos el juego
        Game game = new Game();
        CustomGameUpdateDTO updateDTO = new CustomGameUpdateDTO();
        GameResponseDTO responseDTO = new GameResponseDTO(null,null,null,null,null,null,null);

        //Buscamos el juegp
        when(gamesRepository.findById(1L)).thenReturn(Optional.of(game));
        //Lo retornamos
        when(gamesRepository.save(game)).thenReturn(game);
        //Lo mapeamos
        when(gameMapper.toResponseDTO(game)).thenReturn(responseDTO);

        //LLamamos al método para actualizar el juego
        GameResponseDTO result = gameService.updateGame(1L, updateDTO);

        //Verificamos no nulos, mapeos para guardar en BD e interacciones con la misma
        assertNotNull(result);
        verify(gameMapper).updateEntity(updateDTO, game);
        verify(gamesRepository).save(game);
    }


    @Test
    void deleteCustomGame_JuegoNoEncontradoException() {
        when(gamesRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(JuegoNoEncontradoException.class,
                () -> gameService.deleteCustomGame(1L));
        verify(gamesRepository, never()).delete(any());
    }

    @Test
    void deleteCustomGame_ok() {
        Game game = new Game();

        when(gamesRepository.findById(1L)).thenReturn(Optional.of(game));

        gameService.deleteCustomGame(1L);

        verify(gamesRepository).delete(game);
    }
}