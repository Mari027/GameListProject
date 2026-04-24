package org.gamelist.gamelistapirest.Exceptions;


/**
 * Excepción referente a la introducción incorrecta de datos
 *
 * @author María del Carmen F.
 * */
public class DatosNoCorrectosException extends RuntimeException {
    public DatosNoCorrectosException(String message) {
        super(message);
    }
}
