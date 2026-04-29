package org.gamelist.gamelistapirest.Entities.ExternalApiResponse.vo;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Clase para obtener los datos concretos de los generos
 *
 * @author Maria Del Carmen F.
 * */
public class Genre {
    private Long id;
    private String name;
}
