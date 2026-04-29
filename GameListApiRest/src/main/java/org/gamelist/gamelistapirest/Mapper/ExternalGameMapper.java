package org.gamelist.gamelistapirest.Mapper;

import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameSummaryDTO;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse.ExternalApiResponse;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse.vo.Genre;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

@Component
public class ExternalGameMapper {
    public ExternalGameResponseDTO toResponseDTO(ExternalApiResponse response) {
        String developers = "";
        if (response.getDevelopers() != null && !response.getDevelopers().isEmpty()) {
            developers = response.getDevelopers().get(0).getName();
        }

        List<String> genres = new ArrayList<>();
        if (response.getGenres() != null) {
            genres = response.getGenres().stream()
                    .map(Genre::getName)
                    .toList();
        }

        List<String> platforms = new ArrayList<>();
        if (response.getPlatforms() != null) {
            platforms = response.getPlatforms().stream()
                    .map(pw -> pw.getPlatform().getName())
                    .toList();
        }

        LocalDate releaseDate = null;
        if (response.getReleased() != null) {
            releaseDate = LocalDate.parse(response.getReleased());
        }

        ExternalGameResponseDTO dto = new ExternalGameResponseDTO();
        dto.setId(response.getId());
        dto.setDevelopers(developers);
        dto.setName(response.getName());
        dto.setBackgroundImage(response.getBackground_image());
        dto.setDescription_raw(response.getDescription_raw());
        dto.setReleased(releaseDate);
        dto.setMetacritic(response.getMetacritic());
        dto.setGenres(genres);
        dto.setPlatforms(platforms);

        return dto;
    }
    public ExternalGameSummaryDTO toSummaryResponseDTO(ExternalApiResponse response) {

        ExternalGameSummaryDTO externalGameSummaryDTO = new ExternalGameSummaryDTO();
        externalGameSummaryDTO.setId(response.getId());
        externalGameSummaryDTO.setName(response.getName());
        externalGameSummaryDTO.setBackgroundImage(response.getBackground_image());
        externalGameSummaryDTO.setDescription_raw(response.getDescription_raw());

        return externalGameSummaryDTO;
    }
}
