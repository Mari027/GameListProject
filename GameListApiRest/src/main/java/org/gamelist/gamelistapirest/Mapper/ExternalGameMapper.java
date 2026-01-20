package org.gamelist.gamelistapirest.Mapper;

import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse;
import org.gamelist.gamelistapirest.Entities.Game;

public class ExternalGameMapper {
    public ExternalGameResponseDTO toResponseDTO(ExternalApiResponse response){
        return new ExternalGameResponseDTO(
                response.getName(),
                response.getDescription_raw(),
                response.getReleased(),
                response.getDevelopers(),
                response.getBackground_image(),
        );
    }
}
