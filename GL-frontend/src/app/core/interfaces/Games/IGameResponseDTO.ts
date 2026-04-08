export interface IGameResponseDTO{
    id: number;
    title: string;
    description: string | null;
    released: string | null;
    developer: string | null;
    isCustom: boolean;
}