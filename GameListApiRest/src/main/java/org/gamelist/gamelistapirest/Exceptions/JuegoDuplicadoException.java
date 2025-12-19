package org.gamelist.gamelistapirest.Exceptions;

public class JuegoDuplicadoException extends RuntimeException {
    public JuegoDuplicadoException(String message) {
        message = "Juego Duplicado";
    }
}
