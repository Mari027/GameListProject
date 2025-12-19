package org.gamelist.gamelistapirest.Controller;

import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.UserGamesDTOs.UserGamesResponseDTO;
import org.gamelist.gamelistapirest.Service.UserGamesService.UserGamesService;
import org.gamelist.gamelistapirest.Service.UserGamesService.UserGamesServiceImpl;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/userGames")
public class UserGamesController {

    private final UserGamesServiceImpl userGamesService;

    //ENDPOINT DE LECTURA
    @GetMapping("{userId}")
    public ResponseEntity<UserGamesResponseDTO> getUserGame(@PathVariable Long userId) {
        return ResponseEntity.ok();
    }


}
