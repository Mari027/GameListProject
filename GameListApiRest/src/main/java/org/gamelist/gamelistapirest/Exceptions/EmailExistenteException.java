package org.gamelist.gamelistapirest.Exceptions;

public class EmailExistenteException extends RuntimeException {
    public EmailExistenteException(String message) {

        message = "Email existente";
    }
}
