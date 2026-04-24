package org.gamelist.gamelistapirest.Entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;


/**
 * Clase para el manejo de la lista de juego obtenidos de la API
 * */
@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
public class ExternalApiListResponse {

    /**
     * Cantidad de juegos
     * */
    private Integer count;
    /**
     * Para la pagínacion
     * */
    private String next;
    private String previous;
    /**Lista de juegos*/
    private List<ExternalApiResponse> results;

}

