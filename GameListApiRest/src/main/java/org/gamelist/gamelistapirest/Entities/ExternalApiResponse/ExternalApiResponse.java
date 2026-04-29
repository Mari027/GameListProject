package org.gamelist.gamelistapirest.Entities.ExternalApiResponse;

import lombok.*;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse.vo.Developer;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse.vo.Genre;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse.vo.PlatformWrapper;

import java.util.List;


/**
 * Clase para el manejo de los juegos obtenidos de la API de manera individual
 * */
@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExternalApiResponse {
    /**Id del juego*/
    private Long id;
    /**Nombre del juego*/
    private String name;
    /**Descripción del juego*/
    private String description_raw;
    /**Fecha de salida*/
    private String released;
    /**Imágen*/
    private String background_image;
    /**Nota de metacritic*/
    private Integer metacritic;
    /**Desarrolladoras del juegos*/
    private List<Developer> developers;
    /**Géneros del juegos*/
    private List<Genre> genres;
    /**Plataformas del juegos*/
    private List<PlatformWrapper> platforms;

}
