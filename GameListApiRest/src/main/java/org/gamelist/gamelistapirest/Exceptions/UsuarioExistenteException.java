package org.gamelist.gamelistapirest.Exceptions;

/**
 * Excepción referente a la ya existencia de un usuario por su nombre en BD
 *
 * @author María del Carmen F.
 * */
public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String message) {
        super(message);
    }
}
