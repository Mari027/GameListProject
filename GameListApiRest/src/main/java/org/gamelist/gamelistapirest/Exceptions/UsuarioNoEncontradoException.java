package org.gamelist.gamelistapirest.Exceptions;

/**
 * Excepción referente a la ausencia de un usuario al buscar a este
 *
 * @author María del Carmen F.
 * */
public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String message) {
        super(message);
    }
}
