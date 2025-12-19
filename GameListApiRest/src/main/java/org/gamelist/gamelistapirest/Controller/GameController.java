package org.gamelist.gamelistapirest.Controller;


import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameCreationDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameUpdateDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserResponseDTO;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Service.GamesService.GameServiceImpl;
import org.gamelist.gamelistapirest.Service.UserService.UserServiceImpl;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/game")
public class GameController {
    private final GameServiceImpl gameService;
    private final UserServiceImpl userService;

    //ENDPOINTS DE LECTURA
    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponseDTO> getGame(@PathVariable Long gameId) {
        GameResponseDTO game = gameService.getGameById(gameId);
        return ResponseEntity.ok(game);
    }

    @GetMapping("/allGames")
    public ResponseEntity<List<GameResponseDTO>> getAllGames() {
        List<GameResponseDTO> game = gameService.getAllGames();
        return ResponseEntity.ok(game);
    }

    @GetMapping("/{developer}")
    public ResponseEntity<List<GameResponseDTO>> getAllGamesByDeveloper(@PathVariable String developer) {
        List<GameResponseDTO> gamesByDeveloper = gameService.getGamesByDeveloper(developer);
        return ResponseEntity.ok(gamesByDeveloper);
    }

    @GetMapping("/{releaseDate}")
    public ResponseEntity<List<GameResponseDTO>> getAllGamesByReleaseDate(@PathVariable LocalDate releaseDate) {
        List<GameResponseDTO> gamesByReleaseDate = gameService.getGamesByReleaseDate(releaseDate);
        return  ResponseEntity.ok(gamesByReleaseDate);
    }

    //ENDPOINTS DE CREACIÓN

    @PostMapping("/{userId}/games")
    public ResponseEntity<GameResponseDTO> createCustomGame(@RequestBody CustomGameCreationDTO  customGameCreationDTO,@PathVariable Long userId) {
        UserResponseDTO user = userService.getUserById(userId);
        GameResponseDTO createdGame = gameService.createCustomGame(customGameCreationDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);
    }

    //ENDPOINT DE UPDATE
    @PutMapping("/{gameId}")
    public ResponseEntity<GameResponseDTO> updateGame(@PathVariable Long gameId, @RequestBody CustomGameUpdateDTO customGameUpdateDTO) {
        GameResponseDTO game = gameService.updateGame(gameId,customGameUpdateDTO);
        return ResponseEntity.status(HttpStatus.ACCEPTED).body(game);
    }

    //ENDPOINT DE DELETE
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
        gameService.deleteCustomGame(gameId);
        return ResponseEntity.noContent().build();
    }
}
