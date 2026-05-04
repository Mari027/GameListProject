package org.gamelist.gamelistapirest.Mapper;

import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameSummaryDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameCreationDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameUpdateDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameSummaryDTO;
import org.gamelist.gamelistapirest.Entities.Game;
import org.gamelist.gamelistapirest.Entities.User;
import org.springframework.stereotype.Component;

/**
 * Mapper que convierte juegos creados usadno su DTO {@link CustomGameCreationDTO} a Entidad {@link Game} y viceversa,
 * así como convierte un juego Externo {@link ExternalGameResponseDTO} a entidad {@link Game} y cobierte unjuego de la entidad a Respuesta
 * {@link GameResponseDTO}.
 *
 * También se encarga de mapear lac actualizaciones de un juego a entidad así como de mapear los juegos con una lista de parámetros reducidos
 * @author María del Carmen F.
 * */
@Component
public class GameMapper {
    public Game toEntity(CustomGameCreationDTO dto, User user){
        Game game = new Game();
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        game.setReleaseDate(dto.getReleaseDate());
        game.setDeveloper(dto.getDeveloper());
        game.setImageUrl(dto.getImageUrl());

        game.setUserCreated(true);
        game.setCreatedBy(user);
        return game;
    }

    public Game toEntityFromApi(ExternalGameResponseDTO dto, Long externalGameId){
        Game game = new Game();
        game.setExternalId(externalGameId);
        game.setTitle(dto.getName());
        game.setDescription(dto.getDescription_raw());
        game.setReleaseDate(dto.getReleased());
        game.setDeveloper(dto.getDevelopers());
        game.setImageUrl(dto.getBackgroundImage());

        game.setUserCreated(false);
        game.setCreatedBy(null);
        return game;
    }

    public GameResponseDTO toResponseDTO(Game game){
        return new GameResponseDTO(
                game.getId(),
                game.getTitle(),
                game.getDescription(),
                game.getReleaseDate(),
                game.getDeveloper(),
                game.getImageUrl(),
                game.isUserCreated()
        );
    }
    public Game updateEntity(CustomGameUpdateDTO dto, Game game){
        game.setTitle(dto.getTitle());
        game.setDescription(dto.getDescription());
        game.setReleaseDate(dto.getReleaseDate());
        game.setDeveloper(dto.getDeveloper());
        game.setImageUrl(dto.getImageUrl());
        return game;
    }

    public GameSummaryDTO toSummaryDTO(Game game){
        return new GameSummaryDTO(
                game.getId(),
                game.getTitle(),
                game.getImageUrl(),
                game.getExternalId()
        );
    }

}
