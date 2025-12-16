package org.gamelist.gamelistapirest.Exceptions;

public class UsuarioExistenteException extends RuntimeException {
    public UsuarioExistenteException(String message) {

        message = "Usuario existente";
    }
}
