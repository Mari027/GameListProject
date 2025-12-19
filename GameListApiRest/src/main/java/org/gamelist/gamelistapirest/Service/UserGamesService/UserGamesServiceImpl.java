package org.gamelist.gamelistapirest.Service.UserGamesService;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
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
import org.gamelist.gamelistapirest.Mapper.UserGamesMapper;
import org.gamelist.gamelistapirest.Repository.GamesRepository;
import org.gamelist.gamelistapirest.Repository.UserGamesRepository;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class UserGamesServiceImpl implements UserGamesService {

    private final UserGamesRepository userGamesRepository;
    private final UserRepository userRepository;
    private final GamesRepository gameRepository;
    private final UserGamesMapper userGamesMapper;


    //Si algo falla hace rollback
    @Transactional
    @Override
    public UserGamesResponseDTO addGameToUserList(Long userId, UserGamesRequestDTO requestDTO) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UsuarioNoEncontradoException("Usuario no encontrado"));
        Game game = gameRepository.findById(requestDTO.getGameId()).orElseThrow(()-> new JuegoNoEncontradoException("Juego no encontrado"));

        if(userGamesRepository.existsByUserIdAndGameId(userId, game.getId())) {
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
    public List<UserGamesResponseDTO> getUserGamesByUserId(Long userId) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UsuarioNoEncontradoException("Usuario no encontrado"));

        return userGamesRepository.findByUserId(userId)
                .stream()
                .map(userGamesMapper::toUserGamesResponseDTO)
                .toList();
    }

    @Override
    public List<UserGamesResponseDTO> getUserGamesByReleaseDate(Long userId, LocalDate releaseDate) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UsuarioNoEncontradoException("Usuario no encontrado"));

        return userGamesRepository.findByUserId(userId)
                .stream()
                .filter(userGame -> userGame.getGame().getReleaseDate() != null
                        && userGame.getGame().getReleaseDate().equals(releaseDate))
                .map(userGamesMapper::toUserGamesResponseDTO)
                .toList();
    }

    @Override
    public List<UserGamesResponseDTO> getUserGamesByStatus(Long userId, GameStatus status) {
        User user = userRepository.findById(userId).orElseThrow(()-> new UsuarioNoEncontradoException("Usuario no encontrado"));

        return userGamesRepository.findByUserId(userId)
                .stream()
                .filter(userGame -> userGame.getStatus().equals(status))
                .map(userGamesMapper::toUserGamesResponseDTO)
                .toList();
    }

    @Transactional
    @Override
    public UserGamesResponseDTO updateUserGame(Long userId,Long gameId, UserGamesUpdateRequestDTO requestDTO) {

        User user = userRepository.findById(userId).orElseThrow(()->new UsuarioNoEncontradoException("Usuario no encontrado"));
        Game game = gameRepository.findById(gameId).orElseThrow(()-> new JuegoNoEncontradoException("Juego no encontrado"));

        UserGames userGames = userGamesMapper.updateEntity(requestDTO,user,game);
        UserGames updatedUserGames = userGamesRepository.save(userGames);
        return  userGamesMapper.toUserGamesResponseDTO(updatedUserGames);
    }

    @Transactional
    @Override
    public void removeGameFromUserList(Long userId, Long gameId) {
        if(userHasGame(userId,gameId)) {
            userGamesRepository.deleteByUserIdAndGameId(userId,gameId);
        }
    }

    @Override
    public boolean userHasGame(Long userId, Long gameId) {
        return userGamesRepository.existsByUserIdAndGameId(userId, gameId);
    }
}
