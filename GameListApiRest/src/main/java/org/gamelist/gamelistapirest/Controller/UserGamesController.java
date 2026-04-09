package org.gamelist.gamelistapirest.Controller;

import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesRequestDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesResponseDTO;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesUpdateRequestDTO;
import org.gamelist.gamelistapirest.Enums.GameStatus;
import org.gamelist.gamelistapirest.Security.AuthUtils;
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
    //Usamos authUtils para identificar el usuario autenticado
    private final AuthUtils authUtils;


    //ENDPOINT ADD_GAME
    @PostMapping
    public ResponseEntity<UserGamesResponseDTO> addGametoUserList( @RequestBody UserGamesRequestDTO requestDTO) {
        //Recogemos el usuario autenticado y cogemos su id
        Long userId = authUtils.getAuthenticatedUser().getId();
        UserGamesResponseDTO addedGame = userGamesService.addGameToUserList(userId,requestDTO);
        return ResponseEntity.status(HttpStatus.CREATED).body(addedGame);
    }

    //ENDPOINT DE LECTURA
    @GetMapping
    public ResponseEntity<List<UserGamesResponseDTO>> getUserGames(@RequestParam(required = false) @DateTimeFormat(pattern = "dd-MM-yyyy") LocalDate releaseDate,
                                                                   @RequestParam(required = false) GameStatus gameStatus,
                                                                   @RequestParam(required = false) String search) {
        //Recogemos el usuario autenticado y cogemos su id
        Long userId = authUtils.getAuthenticatedUser().getId();
        List<UserGamesResponseDTO> userGames = userGamesService.getUserGames(userId, releaseDate, gameStatus, search);
        return ResponseEntity.ok(userGames);
    }

    //ENDPOINT UPDATE
    @PutMapping("/games/{gameId}")
    public ResponseEntity<UserGamesResponseDTO> updateUserGames(@PathVariable Long gameId, @RequestBody UserGamesUpdateRequestDTO requestDTO) {
        //Recogemos el usuario autenticado y cogemos su id
        Long userId = authUtils.getAuthenticatedUser().getId();
        UserGamesResponseDTO updatedUserGames = userGamesService.updateUserGame(userId, gameId, requestDTO);
        return ResponseEntity.ok(updatedUserGames);
    }

    //ENDPOINT DELETE
    @DeleteMapping("/games/{gameId}")
    public ResponseEntity<Void> deleteUserGame(@PathVariable Long gameId) {
        //Recogemos el usuario autenticado y cogemos su id
        Long userId = authUtils.getAuthenticatedUser().getId();
        userGamesService.removeGameFromUserList(userId,gameId);
        return ResponseEntity.noContent().build();
    }




}
