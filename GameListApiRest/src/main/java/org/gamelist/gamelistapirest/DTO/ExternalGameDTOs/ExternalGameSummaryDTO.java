package org.gamelist.gamelistapirest.DTO.ExternalGameDTOs;

import lombok.Data;

@Data
public class ExternalGameSummaryDTO {
    private Long id;
    private String name;
    private String description_raw;
    private String backgroundImage;
}
