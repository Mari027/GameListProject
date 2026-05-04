package org.gamelist.gamelistapirest.DTO.CsvDTO;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
/**
 * DTO para el registro de un CSV que nos aporta el número de juegos importados así como juegos omitidos debido a la previa existencia de los mismos en la biblioteca
 *
 * @author María del Carmen F.
 * */
public class CsvImportResult {
    private int imported;
    private int skipped;
}