package org.gamelist.gamelistapirest.DTO.ExternalGameDTOs;

import lombok.Data;

import java.time.LocalDate;

@Data
public class ExternalGameResponseDTO {
    private String name;
    private String description_raw;
    private LocalDate released;
    private String developers;
    private String backgroundImage;
}
