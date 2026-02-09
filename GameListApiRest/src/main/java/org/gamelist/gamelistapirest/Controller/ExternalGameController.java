package org.gamelist.gamelistapirest.Controller;

import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameResponseDTO;
import org.gamelist.gamelistapirest.DTO.ExternalGameDTOs.ExternalGameSummaryDTO;
import org.gamelist.gamelistapirest.DTO.GamesDTOs.GameResponseDTO;
import org.gamelist.gamelistapirest.Service.ExternalGamesService.ExternalGameService;
import org.springframework.format.annotation.DateTimeFormat;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RequiredArgsConstructor
@RestController
@RequestMapping("/api/external_games")
public class ExternalGameController {
    private final ExternalGameService externalGameService;

    //ENDPOINTS DE LECTURA
    @GetMapping("/{id}")
    public ResponseEntity<ExternalGameResponseDTO> getGame(@PathVariable Long id) {
        ExternalGameResponseDTO game = externalGameService.getGameById(id);
        return ResponseEntity.ok(game);
    }

    @GetMapping()
    public ResponseEntity<List<ExternalGameSummaryDTO>> getAllGames(
            @RequestParam(required = true) Integer page,
            @RequestParam(required = true) Integer size
    ) {
        List<ExternalGameSummaryDTO> games = externalGameService.getAllGames(page, size);
        return ResponseEntity.ok(games);
    }
    @GetMapping("/carousel")
    public ResponseEntity<List<ExternalGameSummaryDTO>> getCarouselGames() {
        List<ExternalGameSummaryDTO> games = externalGameService.getCarouselGames();
        return ResponseEntity.ok(games);
    }

}
