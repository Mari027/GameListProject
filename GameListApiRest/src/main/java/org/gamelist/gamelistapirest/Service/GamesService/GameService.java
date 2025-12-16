package org.gamelist.gamelistapirest.Service.GamesService;

import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameCreationDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameUpdateDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.Entities.User;

import java.time.LocalDate;
import java.util.List;

public interface GameService {

    //CREATE
    GameResponseDTO createCustomGame(CustomGameCreationDTO gameCreationDTO, User user);

    //READ
    GameResponseDTO getGameById(Long id);

    List<GameResponseDTO> getAllGames();

    List<GameResponseDTO> getGamesByDeveloper(String developer);

    List<GameResponseDTO> getGamesByReleaseDate(LocalDate releaseDate);

    //UPDATE
    GameResponseDTO updateGame(Long id, CustomGameUpdateDTO gameUpdateDTO);
    //DELETE
    void deleteCustomGame(Long gameId, Long userId);


}
