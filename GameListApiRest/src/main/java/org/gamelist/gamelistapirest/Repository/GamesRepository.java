package org.gamelist.gamelistapirest.Repository;

import org.gamelist.gamelistapirest.Entities.Games;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;
import java.util.Optional;

@Repository
public interface GamesRepository extends JpaRepository<Games, Long> {
    Optional<Games> findByTitle(String title);

    List<Games> findByDeveloper(String developer);

    List<Games> findByReleaseDate(LocalDate releaseDate);

    boolean existsByTitle(String title);
    boolean existsByDeveloper(String developer);
    boolean existsByReleaseDate(LocalDate releaseDate);
}
