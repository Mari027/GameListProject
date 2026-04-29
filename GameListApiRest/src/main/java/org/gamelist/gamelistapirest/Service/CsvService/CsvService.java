package org.gamelist.gamelistapirest.Service.CsvService;

import com.opencsv.CSVWriter;
import com.opencsv.CSVReader;
import lombok.RequiredArgsConstructor;
import org.gamelist.gamelistapirest.DTO.CsvDTO.CsvImportResult;
import org.gamelist.gamelistapirest.Entities.Game;
import org.gamelist.gamelistapirest.Entities.User;
import org.gamelist.gamelistapirest.Entities.UserGames;
import org.gamelist.gamelistapirest.Enums.GameStatus;
import org.gamelist.gamelistapirest.Exceptions.UsuarioNoEncontradoException;
import org.gamelist.gamelistapirest.Repository.GamesRepository;
import org.gamelist.gamelistapirest.Repository.UserGamesRepository;
import org.gamelist.gamelistapirest.Repository.UserRepository;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.*;
import java.time.LocalDate;
import java.util.List;

@Service
@RequiredArgsConstructor
public class CsvService {

    private final UserGamesRepository userGamesRepository;
    private final UserRepository userRepository;
    private final GamesRepository gamesRepository;

    public byte[] exportUserGames(Long userId) throws Exception {

        //Comprobamos que existe
        userRepository.findById(userId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        //Biblioteca del usuario
        List<UserGames> userGames = userGamesRepository.findByUserId(userId);

        ByteArrayOutputStream outputStream = new ByteArrayOutputStream();
        OutputStreamWriter writer = new OutputStreamWriter(outputStream);
        CSVWriter csvWriter = new CSVWriter(writer);

        // Cabecera del CSV
        csvWriter.writeNext(new String[]{
                "title", "status", "rating", "hoursPlayed",
                "startedAt", "completedAt", "review"
        });

        // Filas con los datos de cada juego
        for (UserGames ug : userGames) {
            csvWriter.writeNext(new String[]{
                    ug.getGame().getTitle(),
                    ug.getStatus().name(),
                    ug.getRating() != null ? ug.getRating().toString() : "",
                    ug.getHoursPlayed() != null ? ug.getHoursPlayed().toString() : "",
                    ug.getStartedAt() != null ? ug.getStartedAt().toString() : "",
                    ug.getCompletedAt() != null ? ug.getCompletedAt().toString() : "",
                    ug.getReview() != null ? ug.getReview() : ""
            });
        }

        csvWriter.close();
        return outputStream.toByteArray(); //Delvolvemos el array de bytes
    }


    public CsvImportResult importUserGames(Long userId, MultipartFile file) throws Exception {

        User user = userRepository.findById(userId)
                .orElseThrow(() -> new UsuarioNoEncontradoException("Usuario no encontrado"));

        int imported = 0;
        int skipped = 0;

        InputStreamReader reader = new InputStreamReader(file.getInputStream());
        CSVReader csvReader = new CSVReader(reader);

        // Saltamos la cabecera
        csvReader.readNext();

        String[] line;
        while ((line = csvReader.readNext()) != null) {
            if (line.length < 2) continue;

            String title = line[0];
            String statusStr = line[1];

            List<Game> games = gamesRepository.findAllByTitle(title);
            Game game = games.isEmpty() ? null : games.get(0);

            // Si el juego no existe en BD lo creamos
            if (game == null) {
                game = new Game();
                game.setTitle(title);
                game.setUserCreated(true);
                game.setCreatedBy(user);
                game = gamesRepository.save(game);
            }

            // Si el usuario ya tiene ese juego lo saltamos
            if (userGamesRepository.existsByUserIdAndGameId(userId, game.getId())) {
                skipped++;
                continue;
            }

            // Construimos la relación UserGames
            UserGames userGames = new UserGames();
            userGames.setUser(user);
            userGames.setGame(game);

            try {
                userGames.setStatus(GameStatus.valueOf(statusStr));
            } catch (IllegalArgumentException e) {
                userGames.setStatus(GameStatus.PLAN_TO_PLAY);
            }

            userGames.setRating(line.length > 2 && !line[2].isEmpty() ? Integer.parseInt(line[2]) : null);
            userGames.setHoursPlayed(line.length > 3 && !line[3].isEmpty() ? Double.parseDouble(line[3]) : null);
            userGames.setStartedAt(line.length > 4 && !line[4].isEmpty() ? LocalDate.parse(line[4]) : null);
            userGames.setCompletedAt(line.length > 5 && !line[5].isEmpty() ? LocalDate.parse(line[5]) : null);
            userGames.setReview(line.length > 6 ? line[6] : null);

            userGamesRepository.save(userGames);
            imported++;
        }

        csvReader.close();
        return new CsvImportResult(imported, skipped);
    }
}