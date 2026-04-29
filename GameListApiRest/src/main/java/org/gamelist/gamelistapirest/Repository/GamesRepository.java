package org.gamelist.gamelistapirest.Repository;

import org.gamelist.gamelistapirest.Entities.Game;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GamesRepository extends JpaRepository<Game, Long> {

    Optional<Game> findByTitle(String title);
    Optional<Game> findByExternalId(Long externalId);
    List<Game> findAllByTitle(String title);

    List<Game> findByDeveloper(String developer);

    List<Game> findByReleaseDate(LocalDate releaseDate);

    boolean existsByTitle(String title);
    boolean existsByDeveloper(String developer);
    boolean existsByReleaseDate(LocalDate releaseDate);

}
