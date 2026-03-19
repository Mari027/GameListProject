package org.gamelist.gamelistapirest.Entities;

import lombok.*;

import java.util.List;


@AllArgsConstructor
@NoArgsConstructor
@Setter
@Getter
@Builder
public class ExternalApiResponse {
    private Long id;
    private String name;
    private String description_raw;
    private String released;
    private String background_image;
    private List<Developer> developers;
    @Setter
    @Getter
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    public static class Developer {
        private Long id;
        private String name;
    }
}
