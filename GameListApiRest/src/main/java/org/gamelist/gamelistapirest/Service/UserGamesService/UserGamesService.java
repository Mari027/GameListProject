package org.gamelist.gamelistapirest.Service.UserGamesService;

import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesRequestDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesUpdateRequestDTO;
import org.gamelist.gamelistapirest.Enums.GameStatus;
import org.springframework.cglib.core.Local;

import java.time.LocalDate;
import java.util.List;

public interface UserGamesService {

    //Agregar juego
    UserGamesResponseDTO addGameToUserList(Long userId, UserGamesRequestDTO requestDTO);

    //READ
    List<UserGamesResponseDTO> getUserGames(Long userId, LocalDate releaseDate, GameStatus gameStatus, String title);

    //UPDATE
    UserGamesResponseDTO updateUserGame(Long userId,Long gameId, UserGamesUpdateRequestDTO requestDTO);

    //DELETE
    void removeGameFromUserList(Long userId, Long gameId);

    boolean userHasGame(Long userId, Long gameId);
}
