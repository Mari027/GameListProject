export interface IGameSummary {
    id: number;
    title: string;
    coverImageUrl: string;
}

export interface IUserGame {
    id: number;
    game: IGameSummary;
    gameStatus: string;
    rating: number;
    hoursPlayed: number;
    startedAt: string;
    completedAt: string;
    review: string;
    createdAt: string;
}