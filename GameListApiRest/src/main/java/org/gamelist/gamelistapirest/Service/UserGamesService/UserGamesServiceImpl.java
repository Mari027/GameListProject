package org.gamelist.gamelistapirest.Service.UserGamesService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesRequestDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesUpdateRequestDTO;
import org.gamelist.gamelistapirest.Entities.Game;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Entities.UserGames;
import org.gamelist.gamelistapirest.Enums.GameStatus;
import org.gamelist.gamelistapirest.Exceptions.JuegoDuplicadoException;
import org.gamelist.gamelistapirest.Exceptions.JuegoNoEncontradoException;
import org.gamelist.gamelistapirest.Exceptions.UsuarioNoEncontradoException;
import org.gamelist.gamelistapirest.Mapper.ExternalGameMapper;
import org.gamelist.gamelistapirest.Mapper.GameMapper;
import org.gamelist.gamelistapirest.Mapper.UserGamesMapper;
import org.gamelist.gamelistapirest.Repository.GamesRepository;
import org.gamelist.gamelistapirest.Repository.UserGamesRepository;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.gamelist.gamelistapirest.Service.ExternalGamesService.ExternalGameService;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Service
@RequiredArgsConstructor
public class UserGamesServiceImpl implements UserGamesService {

    private final UserGamesRepository userGamesRepository;
    private final UserRepository userRepository;
    private final GamesRepository gameRepository;
    private final ExternalGameService externalGameService;
    private final UserGamesMapper userGamesMapper;
    private final GameMapper gameMapper;


    //Si algo falla hace rollback
    @Transactional
    @Override
    public UserGamesResponseDTO addGameToUserList(Long userId, UserGamesRequestDTO requestDTO) {
        User user = userRepository.findById(userId).orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));
        Game game;
        if (requestDTO.getExternalGameId() != null) {
            Optional<Game> existingGame = gameRepository.findByExternalId(requestDTO.getExternalGameId());
            if (existingGame.isPresent()) {
                //Si existe, usarlo directamente
                game = existingGame.get();
            } else {
                //Si no existe, llamar a la API externa y guardarlo
                ExternalGameResponseDTO externalGame = externalGameService.getGameById(requestDTO.getExternalGameId());
                // Convertir externalGame a entidad Game y guardarlo
                game = gameMapper.toEntityFromApi(externalGame, requestDTO.getExternalGameId());
                //Lo guardamos en BD
                game = gameRepository.save(game);
            }
        } else {
            game = gameRepository.findById(requestDTO.getGameId())
                    .orElseThrow(() -> new JuegoNoEncontradoException("Juego no encontrado"));
        }

        if (userGamesRepository.existsByUserIdAndGameId(userId, game.getId())) {
            throw new JuegoDuplicadoException("Juego Duplicado");
        }

        UserGames userGames = UserGames.builder()
                .user(user)
                .game(game)
                .status(requestDTO.getGameStatus())
                .rating(requestDTO.getRating())
                .hoursPlayed(requestDTO.getHoursPlayed())
                .completedAt(requestDTO.getCompletedAt())
                .review(requestDTO.getReview())
                .build();

        userGamesRepository.save(userGames);

        return userGamesMapper.toUserGamesResponseDTO(userGames);
    }


    @Override
    public List<UserGamesResponseDTO> getUserGames(Long userId, LocalDate releaseDate, GameStatus status) {

        userRepository.findById(userId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));


        List<UserGames> userGamesList = userGamesRepository.findByUserId(userId);


        if (releaseDate != null) {
            userGamesList = userGamesList.stream()
                    .filter(ug -> ug.getGame().getReleaseDate() != null &&
                            ug.getGame().getReleaseDate().equals(releaseDate))
                    .toList();
        }

        if (status != null) {
            userGamesList = userGamesList.stream()
                    .filter(ug -> ug.getStatus().equals(status))
                    .toList();
        }


        return userGamesList
                .stream()
                .map(userGamesMapper::toUserGamesResponseDTO)
                .toList();
    }


    @Transactional
    @Override
    public UserGamesResponseDTO updateUserGame(Long userId, Long gameId, UserGamesUpdateRequestDTO requestDTO) {

        // Verificamos que el usuario existe
        userRepository.findById(userId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        // Verificamos que el juego existe
        gameRepository.findById(gameId)
                .orElseThrow(() -> new JuegoNoEncontradoException("Juego no encontrado"));

        // Buscamos el juego en la lista personal del usuario
        UserGames userGames = userGamesRepository
                .findByUserIdAndGameId(userId, gameId)
                .orElseThrow(() -> new JuegoNoEncontradoException("El juego no está en la lista del usuario"));

        // Modificamos la entidad existente
        userGamesMapper.updateEntity(requestDTO, userGames);

        UserGames updatedUserGames = userGamesRepository.save(userGames);
        return userGamesMapper.toUserGamesResponseDTO(updatedUserGames);
    }

    @Transactional
    @Override
    public void removeGameFromUserList(Long userId, Long gameId) {
        if (userHasGame(userId, gameId)) {
            userGamesRepository.deleteByUserIdAndGameId(userId, gameId);
        }
    }

    @Override
    public boolean userHasGame(Long userId, Long gameId) {
        return userGamesRepository.existsByUserIdAndGameId(userId, gameId);
    }
}
