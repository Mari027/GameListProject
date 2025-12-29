package org.gamelist.gamelistapirest.Controller;

import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesRequestDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesUpdateRequestDTO;
import org.gamelist.gamelistapirest.Enums.GameStatus;
import org.gamelist.gamelistapirest.Service.UserGamesService.UserGamesService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userGames")
public class UserGamesController {

    private final UserGamesService userGamesService;


    //ENDPOINT ADD_GAME
    @PostMapping("/{userId}")
    public ResponseEntity<UserGamesResponseDTO> addGametoUserList(@PathVariable Long userId, @RequestBody UserGamesRequestDTO requestDTO) {
        UserGamesResponseDTO addedGame = userGamesService.addGameToUserList(userId,requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGame);
    }

    //ENDPOINT DE LECTURA
    @GetMapping("/{userId}/games")
    public ResponseEntity<List<UserGamesResponseDTO>> getUserGames(@PathVariable Long userId,
                                                                   @RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate releaseDate,
                                                                   @RequestParam(required = false) GameStatus gameStatus) {
        List<UserGamesResponseDTO> userGames = userGamesService.getUserGames(userId, releaseDate, gameStatus);
        return ResponseEntity.ok(userGames);
    }

    //ENDPOINT UPDATE
    @PutMapping("/{userId}/games/{gameId}")
    public ResponseEntity<UserGamesResponseDTO> updateUserGames(@PathVariable Long userId, @PathVariable Long gameId, @RequestBody UserGamesUpdateRequestDTO requestDTO) {
        UserGamesResponseDTO updatedUserGames = userGamesService.updateUserGame(userId, gameId, requestDTO);
        return ResponseEntity.ok(updatedUserGames);
    }

    //ENDPOINT DELETE
    @DeleteMapping("/{userId}/games/{gameId}")
    public ResponseEntity<Void> deleteUserGame(@PathVariable Long userId, @PathVariable Long gameId) {
        userGamesService.removeGameFromUserList(userId,gameId);
        return ResponseEntity.noContent().build();
    }




}
