package org.gamelist.gamelistapirest.Mapper;

import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse;
import org.gamelist.gamelistapirest.Entities.Game;

import java.time.LocalDate;

public class ExternalGameMapper {
    public ExternalGameResponseDTO toResponseDTO(ExternalApiResponse response) {

        String developers = "";
        if (response.getDevelopers() != null && !response.getDevelopers().isEmpty()) {
            developers = response.getDevelopers().get(0).getName();
        }

        LocalDate releaseDate = null;
        ExternalGameResponseDTO externalGameResponseDTO = new ExternalGameResponseDTO();
        externalGameResponseDTO.setDevelopers(developers);
        externalGameResponseDTO.setName(response.getName());
        externalGameResponseDTO.setBackgroundImage(response.getBackground_image());
        externalGameResponseDTO.setDescription_raw(response.getDescription_raw());

        if (response.getReleased() != null) {
            releaseDate = LocalDate.parse(response.getReleased());
        }

        externalGameResponseDTO.setReleased(releaseDate);
        return externalGameResponseDTO;
    }
}
