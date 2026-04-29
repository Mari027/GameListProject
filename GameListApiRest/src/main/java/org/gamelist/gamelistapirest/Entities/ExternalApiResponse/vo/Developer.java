package org.gamelist.gamelistapirest.Entities.ExternalApiResponse.vo;

import lombok.*;

@Setter
@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
/**
 * Clase para obtener los datos concretos de los desarrolladores
 *
 * @author Maria Del Carmen F.
 * */
public class Developer {
    private Long id;
    private String name;
}
