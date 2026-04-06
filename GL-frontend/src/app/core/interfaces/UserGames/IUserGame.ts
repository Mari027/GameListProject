import { IGameSummary } from "../ExternalGame/IGameSummary";


export interface IUserGame {
    id: number;
    game: IGameSummary;
    gameStatus: string;
    rating: number;
    hoursPlayed: number;
    startedAt: string;
    completedAt: string | null | undefined;
    review: string | null | undefined;
}