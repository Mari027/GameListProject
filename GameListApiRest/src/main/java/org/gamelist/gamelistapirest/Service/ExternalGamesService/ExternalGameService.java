package org.gamelist.gamelistapirest.Service.ExternalGamesService;

import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.Entities.ExternalApiResponse;
import org.gamelist.gamelistapirest.Exceptions.JuegoNoEncontradoException;
import org.gamelist.gamelistapirest.Mapper.ExternalGameMapper;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.client.RestTemplate;

import java.time.LocalDate;
import java.util.List;

public class ExternalGameService implements IExternalGamesService {

    private ExternalGameMapper externalGameMapper;
    private final RestTemplate restTemplate = new RestTemplate();

    @Value("${external.api.key}")
    private String apiKey;

    @Value("${external.api.url}")
    private String apiUrl;


    @Override
    public ExternalGameResponseDTO getGameById(Long id) {
        String url = apiUrl + "/" + id + "?key=" + apiKey;

        ExternalApiResponse response = restTemplate.getForObject(url, ExternalApiResponse.class);

        if(response == null){
            throw new JuegoNoEncontradoException("El juego con id: " + id + " no existe");
        }

        return externalGameMapper.toResponseDTO(response);

    }

    //Para mostrar todos lo juegos se debe hacer páginación
    @Override
    public List<ExternalGameResponseDTO> getAllGames(String name, LocalDate released, String developer) {
//        String url = apiUrl + "?key=" + apiKey;
//
//        List<ExternalApiResponse> response = restTemplate.getForObject(url, ExternalApiResponse.class);
//
//        if(response == null){
//            throw new JuegoNoEncontradoException("La lista de juegos no esta disponible");
//        }
//
//
//        return externalGameMapper.toResponseDTO(response);
        return null;
    }
}
