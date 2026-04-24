package org.gamelist.gamelistapirest.Service.UserGamesService;

import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesRequestDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesUpdateRequestDTO;
import org.gamelist.gamelistapirest.Entities.Game;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Entities.UserGames;
import org.gamelist.gamelistapirest.Enums.GameStatus;
import org.gamelist.gamelistapirest.Exceptions.JuegoDuplicadoException;
import org.gamelist.gamelistapirest.Exceptions.JuegoNoEncontradoException;
import org.gamelist.gamelistapirest.Exceptions.JuegoUsuarioNoEncontradoException;
import org.gamelist.gamelistapirest.Exceptions.UsuarioNoEncontradoException;
import org.gamelist.gamelistapirest.Mapper.GameMapper;
import org.gamelist.gamelistapirest.Mapper.UserGamesMapper;
import org.gamelist.gamelistapirest.Repository.GamesRepository;
import org.gamelist.gamelistapirest.Repository.UserGamesRepository;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.gamelist.gamelistapirest.Service.ExternalGamesService.ExternalGameService;
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
class UserGamesServiceImplTest {

    @Mock
    private UserGamesRepository userGamesRepository;

    @Mock
    private UserRepository userRepository;

    @Mock
    private GamesRepository gameRepository;

    @Mock
    private ExternalGameService externalGameService;

    @Mock
    private UserGamesMapper userGamesMapper;

    @Mock
    private GameMapper gameMapper;

    @InjectMocks
    private UserGamesServiceImpl userGamesService;



    @Test
    void addGameToUserList_UsuarioNoEncontradoException() {
        //Buscamos por id y retornamos empty
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        //Lanza UsuarioNoEncontradoException
        assertThrows(UsuarioNoEncontradoException.class,
                () -> userGamesService.addGameToUserList(1L, new UserGamesRequestDTO()));
    }

    @Test
    void addGameToUserList_JuegoNoEncontradoException() {
        UserGamesRequestDTO request = new UserGamesRequestDTO();
        request.setGameId(2L);
        //Buscamos usuario y juego
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(gameRepository.findById(2L)).thenReturn(Optional.empty());
        //Lanza excepción
        assertThrows(JuegoNoEncontradoException.class,
                () -> userGamesService.addGameToUserList(1L, request));
    }

    @Test
    void addGameToUserList_JuegoDuplicadoException() {
        UserGamesRequestDTO request = new UserGamesRequestDTO();
        request.setGameId(1L);

        Game game = new Game();
        game.setId(1L);
        //Buscamos y comprobamos que el juego exista ya en el biblioteca del usuario
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        when(userGamesRepository.existsByUserIdAndGameId(1L, 1L)).thenReturn(true);
        //Lanza excepción
        assertThrows(JuegoDuplicadoException.class,
                () -> userGamesService.addGameToUserList(1L, request));
    }

    @Test
    void addGameToUserList_JuegoInternoExiste_ok() {
        //Creamos un dto con los datos del juego en la biblioteca
        UserGamesRequestDTO request = new UserGamesRequestDTO();
        request.setGameId(1L);
        request.setGameStatus(GameStatus.PLAYING);

        //Creamos un juego con esos datos
        Game game = new Game();
        game.setId(1L);
        //Añadimos esos datos a la biblioteca
        User user = new User();
        UserGames userGames = new UserGames();
        UserGamesResponseDTO responseDTO = new UserGamesResponseDTO(1L, null, GameStatus.PLAYING, null, null, null, null, null, null);

        //Buscamos el usuario y el juego
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(game));
        //Vemos si esta dduplicado
        when(userGamesRepository.existsByUserIdAndGameId(1L, 1L)).thenReturn(false);
        //Guardamos y retornamos la respuesta
        when(userGamesRepository.save(any())).thenReturn(userGames);
        when(userGamesMapper.toUserGamesResponseDTO(any())).thenReturn(responseDTO);
        //Añadimos la respuesta a la biblioteca del usuario
        UserGamesResponseDTO result = userGamesService.addGameToUserList(1L, request);

        //Comprobamos no nulos e interacciones con la BD
        assertNotNull(result);
        verify(userGamesRepository).save(any(UserGames.class));
    }

    @Test
    void addGameToUserList_JuegoExternoEnBD_ok() {
        //Creamos el request con el juego externo (api)
        UserGamesRequestDTO request = new UserGamesRequestDTO();
        request.setExternalGameId(2L);
        request.setGameStatus(GameStatus.COMPLETED);

        //Creamos un juego con ese ID
        Game game = new Game();
        game.setId(2L);
        //Creamos la biblioteca de usuario
        User user = new User();
        UserGames userGames = new UserGames();
        UserGamesResponseDTO responseDTO = new UserGamesResponseDTO(1L, null, GameStatus.COMPLETED, null, null, null, null, null, null);

        //Buscamos usuario y juego externo
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findByExternalId(2L)).thenReturn(Optional.of(game));
        //Comprobamos duplicados
        when(userGamesRepository.existsByUserIdAndGameId(1L, 2L)).thenReturn(false);
        //Guardamos y retornamos la biblioteca
        when(userGamesRepository.save(any())).thenReturn(userGames);
        //Mapeamos el juego con los datos del jugador a  responseDTO
        when(userGamesMapper.toUserGamesResponseDTO(any())).thenReturn(responseDTO);
        //Lo guardamos
        UserGamesResponseDTO result = userGamesService.addGameToUserList(1L, request);
        //Comprobamos no nulos e interacción con BD
        assertNotNull(result);
        verify(externalGameService, never()).getGameById(any());
    }

    @Test
    void addGameToUserList_JuegoExternoNoEnBD_save_ok() {
        //Creamos el request del juego a guardar
        UserGamesRequestDTO request = new UserGamesRequestDTO();
        request.setExternalGameId(2L);
        request.setGameStatus(GameStatus.PLAYING);

        //Creamos el juego
        Game game = new Game();
        game.setId(2L);
        User user = new User();
        ExternalGameResponseDTO externalGame = new ExternalGameResponseDTO();
        UserGames userGames = new UserGames();
        UserGamesResponseDTO responseDTO = new UserGamesResponseDTO(1L, null, GameStatus.PLAYING, null, null, null, null, null, null);

        //Buscamos usuario y juego(no encontrado)
        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(gameRepository.findByExternalId(2L)).thenReturn(Optional.empty());
        //Buscamos en la api
        when(externalGameService.getGameById(2L)).thenReturn(externalGame);
        //Mapeamos juego para guardarlo en BD
        when(gameMapper.toEntityFromApi(externalGame, 2L)).thenReturn(game);
        when(gameRepository.save(game)).thenReturn(game);
        //Comprobamos duplicados
        when(userGamesRepository.existsByUserIdAndGameId(1L, 2L)).thenReturn(false);
        //Retornamos el juego con los datos del jugador
        when(userGamesRepository.save(any())).thenReturn(userGames);
        when(userGamesMapper.toUserGamesResponseDTO(any())).thenReturn(responseDTO);

        //Añadimos en BD
        UserGamesResponseDTO result = userGamesService.addGameToUserList(1L, request);

        //Comprobamos no nulos e interacciones con bd
        assertNotNull(result);
        verify(externalGameService).getGameById(2L);
        verify(gameRepository).save(game);
    }



    @Test
    void getUserGames_UsuarioNoEncontradoException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> userGamesService.getUserGames(1L, null, null, null));
    }

    @Test
    void getUserGames_SinFiltros_ok() {
        User user = new User();
        Game game = new Game();
        game.setTitle("Juego");
        UserGames ug = new UserGames();
        ug.setGame(game);
        ug.setStatus(GameStatus.PLAYING);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userGamesRepository.findByUserId(1L)).thenReturn(List.of(ug));
        when(userGamesMapper.toUserGamesResponseDTO(ug)).thenReturn(new UserGamesResponseDTO(1L, null, GameStatus.PLAYING, null, null, null, null, null, null));

        List<UserGamesResponseDTO> result = userGamesService.getUserGames(1L, null, null, null);

        //Comprobamos el tamñao de la lista devuelta en este caso 1
        assertEquals(1, result.size());
    }

    @Test
    void getUserGames_FiltroTitulo_ok() {
        User user = new User();

        Game game1 = new Game();
        game1.setTitle("Juego");
        UserGames ug1 = new UserGames();
        ug1.setGame(game1);
        ug1.setStatus(GameStatus.PLAYING);

        Game game2 = new Game();
        game2.setTitle("titulo");
        UserGames ug2 = new UserGames();
        ug2.setGame(game2);
        ug2.setStatus(GameStatus.COMPLETED);

        when(userRepository.findById(1L)).thenReturn(Optional.of(user));
        when(userGamesRepository.findByUserId(1L)).thenReturn(List.of(ug1, ug2));
        when(userGamesMapper.toUserGamesResponseDTO(ug1)).thenReturn(new UserGamesResponseDTO(1L, null, GameStatus.PLAYING, null, null, null, null, null, null));

        //Añadimos el parámetro de título a la búsqueda
        List<UserGamesResponseDTO> result = userGamesService.getUserGames(1L, null, null, "Juego");

        //Comprobamos tamaño de lista
        assertEquals(1, result.size());
    }


    @Test
    void updateUserGame_UsuarioNoEncontradoException() {
        when(userRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(UsuarioNoEncontradoException.class,
                () -> userGamesService.updateUserGame(1L, 1L, new UserGamesUpdateRequestDTO()));
    }

    @Test
    void updateUserGame_JuegoNoEncontradoException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(gameRepository.findById(1L)).thenReturn(Optional.empty());

        assertThrows(JuegoNoEncontradoException.class,
                () -> userGamesService.updateUserGame(1L, 1L, new UserGamesUpdateRequestDTO()));
    }

    @Test
    void updateUserGame_JuegoUsuarioNoEncontradoException() {
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(new Game()));
        when(userGamesRepository.findByUserIdAndGameId(1L, 1L)).thenReturn(Optional.empty());

        assertThrows(JuegoUsuarioNoEncontradoException.class,
                () -> userGamesService.updateUserGame(1L, 1L, new UserGamesUpdateRequestDTO()));
    }

    @Test
    void updateUserGame_ok() {
        //Creamps el updateDTO para actualizar la lista del usuario
        UserGames userGames = new UserGames();
        UserGamesUpdateRequestDTO updateDTO = new UserGamesUpdateRequestDTO();
        UserGamesResponseDTO responseDTO = new UserGamesResponseDTO(1L, null, GameStatus.COMPLETED, null, null, null, null, null, null);

        //Buscamos usuario y juego
        when(userRepository.findById(1L)).thenReturn(Optional.of(new User()));
        when(gameRepository.findById(1L)).thenReturn(Optional.of(new Game()));
        //Buscamos duplicados
        when(userGamesRepository.findByUserIdAndGameId(1L, 1L)).thenReturn(Optional.of(userGames));
        when(userGamesRepository.save(userGames)).thenReturn(userGames);
        when(userGamesMapper.toUserGamesResponseDTO(userGames)).thenReturn(responseDTO);

        //actualizamos
        UserGamesResponseDTO result = userGamesService.updateUserGame(1L, 1L, updateDTO);

        //Verificamos no nulos e interacciones con BD
        assertNotNull(result);
        verify(userGamesMapper).updateEntity(updateDTO, userGames);
        verify(userGamesRepository).save(userGames);
    }


    @Test
    void removeGameFromUserList_ok() {
        when(userGamesRepository.existsByUserIdAndGameId(1L, 1L)).thenReturn(true);

        userGamesService.removeGameFromUserList(1L, 1L);

        verify(userGamesRepository).deleteByUserIdAndGameId(1L, 1L);
    }

    @Test
    void removeGameFromUserList_JuegoNoSeEncuentraEnLaLista() {
        when(userGamesRepository.existsByUserIdAndGameId(1L, 1L)).thenReturn(false);

        userGamesService.removeGameFromUserList(1L, 1L);

        //Verificamos que nunca se interactue con BD
        verify(userGamesRepository, never()).deleteByUserIdAndGameId(any(), any());
    }


    @Test
    void userHasGame_true() {
        when(userGamesRepository.existsByUserIdAndGameId(1L, 1L)).thenReturn(true);

        assertTrue(userGamesService.userHasGame(1L, 1L));
    }

    @Test
    void userHasGame_false() {
        when(userGamesRepository.existsByUserIdAndGameId(1L, 1L)).thenReturn(false);

        assertFalse(userGamesService.userHasGame(1L, 1L));
    }
}