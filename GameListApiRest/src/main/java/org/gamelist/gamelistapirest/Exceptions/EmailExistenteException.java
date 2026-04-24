package org.gamelist.gamelistapirest.Exceptions;

/**
 * Excepción referente a la existencia de un usuario con x email en BD
 *
 * @author María del Carmen F.
 * */
public class EmailExistenteException extends RuntimeException {
    public EmailExistenteException(String message) {
        super(message);

    }
}
