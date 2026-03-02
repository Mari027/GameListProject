package org.gamelist.gamelistapirest.Mapper;

import org.gamelist.gamelistapirest.DTO.UserDTOs.UserUpdateDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesRequestDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesUpdateRequestDTO;
import org.gamelist.gamelistapirest.Entities.Game;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Entities.UserGames;
import org.springframework.stereotype.Component;

@Component
public class UserGamesMapper {

    public UserGames toEntity(UserGamesRequestDTO dto, User user, Game game) {
        UserGames userGames = new UserGames();

        userGames.setUser(user);
        userGames.setGame(game);
        userGames.setStatus(dto.getGameStatus());
        userGames.setRating(dto.getRating());
        userGames.setHoursPlayed(dto.getHoursPlayed());
        userGames.setStartedAt(dto.getStartedAt());
        userGames.setCompletedAt(dto.getCompletedAt());
        userGames.setReview(dto.getReview());

        return userGames;
    }

    public void updateEntity(UserGamesUpdateRequestDTO dto, UserGames userGames) {
        userGames.setStatus(dto.getGameStatus());
        userGames.setRating(dto.getRating());
        userGames.setHoursPlayed(dto.getHoursPlayed());
        userGames.setStartedAt(dto.getStartedAt());
        userGames.setCompletedAt(dto.getCompletedAt());
        userGames.setReview(dto.getReview());
    }

    public UserGamesResponseDTO toUserGamesResponseDTO(UserGames userGames) {
        GameMapper gameMapper = new GameMapper();
        return new UserGamesResponseDTO(
                userGames.getId(),
                gameMapper.toSummaryDTO(userGames.getGame()),
                userGames.getStatus(),
                userGames.getRating(),
                userGames.getHoursPlayed(),
                userGames.getStartedAt(),
                userGames.getCompletedAt(),
                userGames.getReview(),
                userGames.getCreatedAt()
        );
    }
}
