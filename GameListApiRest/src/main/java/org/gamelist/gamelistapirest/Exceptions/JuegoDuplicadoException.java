package org.gamelist.gamelistapirest.Exceptions;


/**
 * Excepción para evitar la existencia de juegos duplicados en una misma biblioteca
 *
 * @author María del Carmen F.
 * */
public class JuegoDuplicadoException extends RuntimeException {
    public JuegoDuplicadoException(String message) {
        super(message);
    }
}
