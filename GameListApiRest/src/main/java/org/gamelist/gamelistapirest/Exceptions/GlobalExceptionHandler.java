package org.gamelist.gamelistapirest.Exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.Map;

@RestControllerAdvice
/**
 * Clase encargada de  mandar la información de una excepción al frontend
 * para detallar los posibles fallos
 *
 * @author Maria del Carmen F.
 * */
public class GlobalExceptionHandler {

    @ExceptionHandler(EmailExistenteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleEmailExistente(EmailExistenteException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(UsuarioExistenteException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleUsuarioExistente(UsuarioExistenteException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(UsuarioNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleUsuarioNoEncontrado(UsuarioNoEncontradoException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(JuegoNoEncontradoException.class)
    @ResponseStatus(HttpStatus.NOT_FOUND)
    public Map<String, String> handleJuegoNoEncontrado(JuegoNoEncontradoException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(JuegoDuplicadoException.class)
    @ResponseStatus(HttpStatus.CONFLICT)
    public Map<String, String> handleJuegoDuplicado(JuegoDuplicadoException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(DatosNoCorrectosException.class)
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    public Map<String, String> handleDatosNoCorrectos(DatosNoCorrectosException ex) {
        return Map.of("message", ex.getMessage());
    }

    @ExceptionHandler(CatalogoNoDisponible.class)
    @ResponseStatus(HttpStatus.SERVICE_UNAVAILABLE)
    public Map<String, String> handleCatalogoNoDisponible(CatalogoNoDisponible ex) {
        return Map.of("message", ex.getMessage());
    }
}