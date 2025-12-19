package org.gamelist.gamelistapirest.Repository;

import org.gamelist.gamelistapirest.Entities.UserGames;
import org.gamelist.gamelistapirest.Enums.GameStatus;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UserGamesRepository extends JpaRepository<UserGames, Long> {

    /**
     * Metodo de búsqueda:
     * Lista de juegos de un usuario
     * */
    List<UserGames> findByUserId(Long userID);
    /**
     * Métodos de filtrado
     * 1 - Lista de juegos de un usuario según su estado
     * 2 - Juego de usuario según su ID
     * 3 - Juegos según el rating
     * */

    List<UserGames> findByUserIdAndStatus(Long userId, GameStatus status);

    //Metodo para validar que el usuario no tiene ese juego en la lista
    Optional<UserGames> findByUserIdAndGameId(Long userId, Long gameId);
    List<UserGames> findByUserIdAndRating(Long userId, Integer rating);

    void deleteByUserIdAndGameId(Long userId, Long gameId);

    boolean existsByUserIdAndGameId(Long userId, Long gameId);

}
