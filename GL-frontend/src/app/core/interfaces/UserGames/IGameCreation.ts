export interface IGameCreation{
    title: string;
    description: string | null;
    released: string | null;
    developer: string | null;
    gameStatus: string;
    rating: number;
    hoursPlayed: number;
    startedAt: string | null;
    completedAt: string | null | undefined;
    review: string | null | undefined;
}