export interface IGameRequest{
    externalGameId: number; //Juegos de la api externas añadidos por algún usuario
    gameStatus: string;
    rating: number;
    hoursPlayed: number;
    startedAt: string | null;
    completedAt: string | null;
    review: string | null;
}