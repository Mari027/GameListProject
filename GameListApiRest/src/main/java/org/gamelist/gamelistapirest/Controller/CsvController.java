package org.gamelist.gamelistapirest.Controller;

import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.CsvDTO.CsvImportResult;
import org.gamelist.gamelistapirest.Security.AuthUtils;
import org.gamelist.gamelistapirest.Service.CsvService.CsvService;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

@RestController
@RequestMapping("/api/csv")
@RequiredArgsConstructor
public class CsvController {

    private final CsvService csvService;
    private final AuthUtils authUtils;

    @GetMapping("/export")
    public ResponseEntity<byte[]> exportCsv() throws Exception {
        Long userId = authUtils.getAuthenticatedUser().getId();
        byte[] csvBytes = csvService.exportUserGames(userId);

        return ResponseEntity.ok()
                .header(HttpHeaders.CONTENT_DISPOSITION, "attachment; filename=biblioteca.csv")
                .contentType(MediaType.parseMediaType("text/csv"))
                .body(csvBytes);
    }

    @PostMapping("/import")
    public ResponseEntity<CsvImportResult> importCsv(@RequestParam("file") MultipartFile file) throws Exception {
        Long userId = authUtils.getAuthenticatedUser().getId();
        CsvImportResult result = csvService.importUserGames(userId, file);
        return ResponseEntity.ok(result);
    }
}