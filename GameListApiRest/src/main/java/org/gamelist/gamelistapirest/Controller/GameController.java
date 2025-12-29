package org.gamelist.gamelistapirest.Controller;


import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameCreationDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.CustomGameUpdateDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserDTOs.UserResponseDTO;
import org.gamelist.gamelistapirest.Service.GamesService.GameService;
import org.gamelist.gamelistapirest.Service.UserService.UserService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/games")
public class GameController {
    private final GameService gameService;

    //ENDPOINTS DE LECTURA
    @GetMapping("/{gameId}")
    public ResponseEntity<GameResponseDTO> getGame(@PathVariable Long gameId) {
        GameResponseDTO game = gameService.getGameById(gameId);
        return ResponseEntity.ok(game);
    }

    @GetMapping()
    public ResponseEntity<List<GameResponseDTO>> getAllGames(
            @RequestParam(required = false) String title,
            @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate releaseDate,
            @RequestParam(required = false) String developer
    ) {
        List<GameResponseDTO> game = gameService.getAllGames(title, releaseDate, developer);
        return ResponseEntity.ok(game);
    }

    //ENDPOINTS DE CREACIÓN
    @PostMapping("/custom/{userId}")
    public ResponseEntity<GameResponseDTO> createCustomGame(@RequestBody CustomGameCreationDTO  customGameCreationDTO,@PathVariable Long userId) {
        GameResponseDTO createdGame = gameService.createCustomGame(customGameCreationDTO, userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(createdGame);
    }

    //ENDPOINT DE UPDATE
    @PutMapping("/{gameId}")
    public ResponseEntity<GameResponseDTO> updateGame(@PathVariable Long gameId, @RequestBody CustomGameUpdateDTO customGameUpdateDTO) {
        GameResponseDTO game = gameService.updateGame(gameId,customGameUpdateDTO);
        return ResponseEntity.ok(game);
    }

    //ENDPOINT DE DELETE
    @DeleteMapping("/{gameId}")
    public ResponseEntity<Void> deleteGame(@PathVariable Long gameId) {
        gameService.deleteCustomGame(gameId);
        return ResponseEntity.noContent().build();
    }
}
