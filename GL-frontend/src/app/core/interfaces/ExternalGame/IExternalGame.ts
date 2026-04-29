export interface IExternalGame {
    id: number;
    name: string;
    description_raw: string;
    released: string;
    backgroundImage: string;
    developers: string;
    metacritic: number;
    genres: string[];
    platforms: string[];
}