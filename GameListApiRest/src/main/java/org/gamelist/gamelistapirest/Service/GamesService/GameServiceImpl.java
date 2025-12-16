package org.gamelist.gamelistapirest.Service.GamesService;

import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameCreationDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameUpdateDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.Entities.Game;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Mapper.GameMapper;
import org.gamelist.gamelistapirest.Repository.GamesRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final GamesRepository gamesRepository;
    private final GameMapper gameMapper;

    @Override
    public GameResponseDTO createCustomGame(CustomGameCreationDTO gameCreationDTO, User user) {
        Game game = gameMapper.toEntity(gameCreationDTO, user);

        Game saved = gamesRepository.save(game);

        return gameMapper.toResponseDTO(saved);
    }

    @Override
    public GameResponseDTO getGameById(Long id) {



        return null;
    }

    @Override
    public List<GameResponseDTO> getAllGames() {
        return List.of();
    }

    @Override
    public List<GameResponseDTO> getGamesByDeveloper(String developer) {
        return List.of();
    }

    @Override
    public List<GameResponseDTO> getGamesByReleaseDate(LocalDate releaseDate) {
        return List.of();
    }

    @Override
    public GameResponseDTO updateGame(Long id, CustomGameUpdateDTO gameUpdateDTO) {
        return null;
    }

    @Override
    public void deleteCustomGame(Long gameId, Long userId) {

    }
}
