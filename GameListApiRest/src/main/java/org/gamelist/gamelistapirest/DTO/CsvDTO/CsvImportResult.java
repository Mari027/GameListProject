package org.gamelist.gamelistapirest.DTO.CsvDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class CsvImportResult {
    private int imported;
    private int skipped;
}