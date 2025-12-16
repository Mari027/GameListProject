package org.gamelist.gamelistapirest.Exceptions;

public class JuegoNoEncontradoException extends RuntimeException {
    public JuegoNoEncontradoException(String message) {

        message = "Juego no encontrado";
    }
}
