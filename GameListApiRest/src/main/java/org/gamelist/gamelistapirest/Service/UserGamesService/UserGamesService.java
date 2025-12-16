package org.gamelist.gamelistapirest.Service.UserGamesService;

import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameUpdateDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesRequestDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesUpdateRequestDTO;
import org.gamelist.gamelistapirest.Enums.GameStatus;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

public interface UserGamesService {

    //Agregar juego
    UserGamesResponseDTO addGameToUserList(Long userId, UserGamesRequestDTO requestDTO);

    //READ
    UserGamesResponseDTO getUserGamesByUserId(Long userId);
    UserGamesResponseDTO getUserGamesByReleaseDate(Long userId, LocalDate releaseDate);
    UserGamesResponseDTO getUserGamesByStatus(Long userId, GameStatus status);

    //UPDATE
    UserGamesResponseDTO updateUserGame(Long userGameId, UserGamesUpdateRequestDTO requestDTO);

    //DELETE
    void removeGameFromUserList(Long userId, Long gameId);

    boolean userHasGame(Long userId, Long gameId);
}
