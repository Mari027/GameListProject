package org.gamelist.gamelistapirest.Enums;

/**
 * Esta clase es para el dato Estado que se encuentra en
 * la entidad UserGames
 * */
public enum GameStatus {
    PLAYING,
    PAUSED, //Se empezó pero no se esta jugando por el momento
    COMPLETED,
    DROPPED,
    PLAN_TO_PLAY
}
