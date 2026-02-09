package org.gamelist.gamelistapirest.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
/**
 * Clase para obtener lista de juegos de la API externa
 * */
public class ExternalApiListResponse {

    //Para saber cantidad de juegos recogidos
    private Integer count;
    //Para la paginación de la página siguiente
    private String next;
    //Para la páginación de la página anterior
    private String previous;
    //Lista con los juegos obtenidos
    private List<ExternalApiResponse> results;

}

