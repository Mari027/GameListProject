package org.gamelist.gamelistapirest.Service.GamesService;

import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameCreationDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface GameService {

    GameResponseDTO createCustomGame(GameCreationDTO gameCreationDTO);

    GameResponseDTO getGameById(Long id);

    List<GameResponseDTO> getAllGames();

    List<GameResponseDTO> getGamesByDeveloper(String developer);

    List<GameResponseDTO> getGamesByReleaseDate(LocalDate releaseDate);

    void deleteCustomGame(Long gameId, Long userId);


}
