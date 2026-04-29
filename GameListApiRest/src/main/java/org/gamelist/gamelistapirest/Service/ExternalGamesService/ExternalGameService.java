package org.gamelist.gamelistapirest.Service.ExternalGamesService;

import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameSummaryDTO;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse.ExternalApiListResponse;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse.ExternalApiResponse;
import org.gamelist.gamelistapirest.Exceptions.CatalogoNoDisponible;
import org.gamelist.gamelistapirest.Exceptions.JuegoNoEncontradoException;
import org.gamelist.gamelistapirest.Mapper.ExternalGameMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@Service
@RequiredArgsConstructor
public class ExternalGameService implements IExternalGamesService {

    private final ExternalGameMapper externalGameMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${external.api.key}")
    private String apiKey;

    @Value("${external.api.url}")
    private String apiUrl;


    @Override
    public ExternalGameResponseDTO getGameById(Long id) {
        String url =
                apiUrl
                + "/" + id
                + "?key=" + apiKey;

        ExternalApiResponse response = restTemplate.getForObject(url, ExternalApiResponse.class);

        if(response == null){
            throw new JuegoNoEncontradoException("El juego con id: " + id + " no existe");
        }

        return externalGameMapper.toResponseDTO(response);

    }

    //Para mostrar todos lo juegos se debe hacer páginación
    @Override
    public List<ExternalGameSummaryDTO> getAllGames(int page, int size,String search) {
        String url = apiUrl
                + "?key=" + apiKey
                + "&page=" + page
                + "&page_size=" + size;

        if(search != null && !search.isEmpty()){
            url += "&search=" + search;
        }
        ExternalApiListResponse response = restTemplate.getForObject(url, ExternalApiListResponse.class);
        if (response == null || response.getResults() == null) {
            throw new CatalogoNoDisponible("Catálogo no disponible");
        }
        return response.getResults().stream().map(externalGameMapper::toSummaryResponseDTO).toList();
    }
    //Para el carousel de juegos (LOGIN)
    @Override
    public List<ExternalGameSummaryDTO> getCarouselGames() {
        String url = apiUrl
                + "?key=" + apiKey
                + "&page=1&page_size=5";

        ExternalApiListResponse response =
                restTemplate.getForObject(url, ExternalApiListResponse.class);

        return response.getResults()
                .stream()
                .map(externalGameMapper::toSummaryResponseDTO)
                .toList();
    }


}
