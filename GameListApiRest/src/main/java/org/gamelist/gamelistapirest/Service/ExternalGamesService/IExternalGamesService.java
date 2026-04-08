package org.gamelist.gamelistapirest.Service.ExternalGamesService;

import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameSummaryDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameCreationDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameUpdateDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;

import java.time.LocalDate;
import java.util.List;

public interface IExternalGamesService {

    //READ
    ExternalGameResponseDTO getGameById(Long id);
    List<ExternalGameSummaryDTO> getAllGames(int page, int size,String search);
    List<ExternalGameSummaryDTO> getCarouselGames();

}
