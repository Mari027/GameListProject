import { IGameSummary } from "./IUserGame";

export interface IUserGameUpdate {
    game: IGameSummary;
    gameStatus: string;
    rating: number;
    hoursPlayed: number;
    startedAt: string;
    completedAt: string | null | undefined;
    review: string | null | undefined;
}