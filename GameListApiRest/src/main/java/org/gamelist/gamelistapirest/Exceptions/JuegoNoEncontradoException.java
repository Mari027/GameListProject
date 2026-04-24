package org.gamelist.gamelistapirest.Exceptions;


/**
 * Excepción referente a la ausencia de un juego al buscar este
 *
 * @author María del Carmen F.
 * */
public class JuegoNoEncontradoException extends RuntimeException {
    public JuegoNoEncontradoException(String message) {
        super(message);
    }
}
