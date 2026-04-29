package org.gamelist.gamelistapirest.DTO.ExternalGameDTOs;

import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class ExternalGameResponseDTO {
    private Long id;
    private String name;
    private String description_raw;
    private LocalDate released;
    private String developers;
    private String backgroundImage;
    private Integer metacritic;
    private List<String> genres;
    private List<String> platforms;
}
