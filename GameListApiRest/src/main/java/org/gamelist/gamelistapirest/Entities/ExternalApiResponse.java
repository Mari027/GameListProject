package org.gamelist.gamelistapirest.Entities;

import lombok.*;

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
    /**Desarrolladoras del juegos*/
    private List<Developer> developers;
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    /**
     * Clase estática para obtener los datos concretos de los desarrolladores
     * */
    public static class Developer {
        private Long id;
        private String name;
    }
}
