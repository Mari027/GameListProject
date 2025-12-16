package org.gamelist.gamelistapirest.Exceptions;

public class UsuarioNoEncontradoException extends RuntimeException {
    public UsuarioNoEncontradoException(String message) {

        message = "Usuario no encontrado";
    }
}
