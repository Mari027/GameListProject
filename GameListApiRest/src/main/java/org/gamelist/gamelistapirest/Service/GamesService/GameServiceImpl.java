package org.gamelist.gamelistapirest.Service.GamesService;

import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameCreationDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameUpdateDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserResponseDTO;
import org.gamelist.gamelistapirest.Entities.Game;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Exceptions.DatosNoCorrectosException;
import org.gamelist.gamelistapirest.Exceptions.JuegoNoEncontradoException;
import org.gamelist.gamelistapirest.Exceptions.UsuarioNoEncontradoException;
import org.gamelist.gamelistapirest.Mapper.GameMapper;
import org.gamelist.gamelistapirest.Repository.GamesRepository;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final GamesRepository gamesRepository;
    private final UserRepository userRepository;
    private final GameMapper gameMapper;

    @Override
    public GameResponseDTO createCustomGame(CustomGameCreationDTO gameCreationDTO, Long userId) {
        if(gameCreationDTO.getTitle() == null){
            throw new DatosNoCorrectosException("Campo Obligatorio");
        }

        User user = userRepository.findById(userId).orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        Game game = gameMapper.toEntity(gameCreationDTO, user);

        Game saved = gamesRepository.save(game);

        return gameMapper.toResponseDTO(saved);
    }

    @Override
    public GameResponseDTO getGameById(Long id) {
        Game game = gamesRepository.findById(id).orElseThrow(()-> new JuegoNoEncontradoException("Juego no encontrado"));
        return gameMapper.toResponseDTO(game);
    }

    @Override
    public List<GameResponseDTO> getAllGames(String title, LocalDate releaseDate, String developer) {

        List<Game> games = gamesRepository.findAll();

        if(title != null){
            games = games.stream()
                    .filter(game->game.getTitle() != null && game.getTitle().equals(title))
                    .toList();
        }
        if(releaseDate != null){
            games = games.stream()
                    .filter(game->game.getReleaseDate() != null && game.getReleaseDate().equals(releaseDate))
                    .toList();
        }
        if(developer != null){
            games = games.stream()
                    .filter(game->game.getDeveloper() != null && game.getDeveloper().equals(developer))
                    .toList();
        }

        return games
                .stream()
                .map(gameMapper::toResponseDTO)
                .toList();
    }


    @Override
    public GameResponseDTO updateGame(Long id, CustomGameUpdateDTO gameUpdateDTO) {
        Game game = gamesRepository.findById(id).orElseThrow(() ->
                new JuegoNoEncontradoException("Juego no encontrado"));
        gameMapper.updateEntity(gameUpdateDTO, game);
        Game updatedGame = gamesRepository.save(game);
        return gameMapper.toResponseDTO(updatedGame);
    }

    @Override
    public void deleteCustomGame(Long gameId) {
        Game game = gamesRepository.findById(gameId).orElseThrow(() ->
                new JuegoNoEncontradoException("Juego no encontrado"));
        gamesRepository.delete(game);
    }
}
