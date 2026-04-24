package org.gamelist.gamelistapirest.Exceptions;

/**
 * Excepción para las llamadas a la api externa referentes a la obtención de un catálogo
 * tanto si hay problemas al llamar a la api como si devuelve un catálogo vacío
 *
 * @author María del Carmen F.
 * */
public class CatalogoNoDisponible extends RuntimeException {
    public CatalogoNoDisponible(String message) {
        super(message);
    }
}
