package org.gamelist.gamelistapirest.Exceptions;


/**
 * Excepción referente a la ausencia de un juego en la biblioteca de un usuario
 *
 * @author María del Carmen F.
 * */
public class JuegoUsuarioNoEncontradoException extends RuntimeException {
    public JuegoUsuarioNoEncontradoException(String message) {
        super(message);
    }
}
