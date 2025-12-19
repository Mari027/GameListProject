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
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class GameServiceImpl implements GameService{

    private final GamesRepository gamesRepository;
    private final GameMapper gameMapper;

    @Override
    public GameResponseDTO createCustomGame(CustomGameCreationDTO gameCreationDTO, Long user) {
        if(gameCreationDTO.getTitle() == null){
            throw new DatosNoCorrectosException("Campo Obligatorio");
        }
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
    public List<GameResponseDTO> getAllGames() {
        return gamesRepository.findAll()
                .stream()
                .map(gameMapper::toResponseDTO)
                .toList();
    }

    @Override
    public List<GameResponseDTO> getGamesByDeveloper(String developer) {
        return gamesRepository.findAll()
                .stream()
                .map(gameMapper::toResponseDTO)
                .filter(gameDTO -> gameDTO.getDeveloper().equals(developer))
                .toList();
    }

    @Override
    public List<GameResponseDTO> getGamesByReleaseDate(LocalDate releaseDate) {
        return gamesRepository.findAll()
                .stream()
                .map(gameMapper::toResponseDTO)
                .filter(gameDTO -> gameDTO.getReleaseDate().equals(releaseDate))
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
