package org.gamelist.gamelistapirest.Repository;

import org.gamelist.gamelistapirest.Entities.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {

    //Optional es por que es posible que devuelva nulo
    Optional<User> findByNickname(String nickname);

    Optional<User> findByEmail(String email);

    /**
     * Estos booleanos ayudan al lanzamiento de excepciones en la capa de servicio
     * */

    boolean existsByNickname(String nickname);

    boolean existsByEmail(String email);
}
