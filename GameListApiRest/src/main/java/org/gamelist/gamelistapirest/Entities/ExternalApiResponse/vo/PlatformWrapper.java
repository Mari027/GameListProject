package org.gamelist.gamelistapirest.Entities.ExternalApiResponse.vo;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Clase para obtener el objeto anidado que nos devuelve la API para la obtención de plataformas.
 *
 * @author Maria Del Carmen F.
 * */
public class PlatformWrapper {
    private Platform platform;

    /**
     * Clase estática auxiliar para obtener los datos concretos de las plataformas
     * */
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Platform{
        private Long id;
        private String name;
    }
}


