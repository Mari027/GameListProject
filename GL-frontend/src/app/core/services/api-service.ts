import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IExternalGameSummary } from '../interfaces/ExternalGame/IExternalGameSummary';
import { ILogin } from '../interfaces/Auth/ILogin';
import { IAuthResponse } from '../interfaces/Auth/IAuthResponse';
import { IRegister } from '../interfaces/Auth/IRegister';
import { IGameRequest } from '../interfaces/ExternalGame/IGameRequest';
import { IUserGame } from '../interfaces/UserGames/IUserGame';
import { IUserGameUpdate } from '../interfaces/UserGames/IUserGameUpdate';
import { IUserResponse } from '../interfaces/UserGames/IUserResponse';
import { IGameCreation } from '../interfaces/UserGames/IGameCreation';
import { IGameResponseDTO } from '../interfaces/Games/IGameResponseDTO';
import { IExternalGame } from '../interfaces/ExternalGame/IExternalGame';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  private apiUrl = 'http://localhost:8080/api/'

  constructor(private http: HttpClient) { }

  //MÉTODOS AUTH
  login(loginRequest: ILogin): Observable<IAuthResponse> {
    //LLamada a la api en POST para login
    return this.http.post<IAuthResponse>(`${this.apiUrl}auth/login`, loginRequest);
  }

  register(registerRequest: IRegister): Observable<IAuthResponse> {
    return this.http.post<IAuthResponse>(`${this.apiUrl}auth/register`, registerRequest);
  }

  //MÉTODOS API EXTERNA

  getAllgames(page: number, size: number, search: string = ''): Observable<IExternalGameSummary[]> {
    return this.http.get<IExternalGameSummary[]>(`${this.apiUrl}external_games?page=${page}&size=${size}&search=${search}`);
  }

  getGameById(id: number): Observable<IExternalGame> {
    return this.http.get<IExternalGame>(`${this.apiUrl}external_games/${id}`);
  }

  //MÉTODOS CRUD JUEGOS

  addGameToList(gameToAdd: IGameRequest) {
    return this.http.post<IGameRequest>(`${this.apiUrl}userGames`, gameToAdd);
  }
  getAllUserGames(search: string = ''): Observable<IUserGame[]> {
    return this.http.get<IUserGame[]>(`${this.apiUrl}userGames?search=${search}`);
  }

  updateGameFromList(id: number, updatedGame: IUserGameUpdate) {
    return this.http.put<IUserGameUpdate>(`${this.apiUrl}userGames/games/${id}`, updatedGame);
  }

  deleteGameFromList(id: number) {
    return this.http.delete(`${this.apiUrl}userGames/games/${id}`);
  }

  createNewGame(game: IGameCreation): Observable<IGameResponseDTO> {
    return this.http.post<IGameResponseDTO>(`${this.apiUrl}games/user-create`, game);
  }

  getCustomGameById(id: number): Observable<IGameResponseDTO> {
    return this.http.get<IGameResponseDTO>(`${this.apiUrl}games/${id}`);
  }

  //MÉTODOS DE USUARIO

  getAllUsers(): Observable<IUserResponse[]> {
    return this.http.get<IUserResponse[]>(`${this.apiUrl}users`);
  }

  deleteUser(id: number) {
    return this.http.delete(`${this.apiUrl}users/${id}`);
  }

  //MÉTODOS CSV
  exportCsv(): Observable<Blob> { //Blob es un objeto que representa datos binarios en bruto
    return this.http.get(`${this.apiUrl}csv/export`, { responseType: 'blob' });
  }

  importCsv(file: File): Observable<any> {
    const formData = new FormData();
    formData.append('file', file);
    return this.http.post(`${this.apiUrl}csv/import`, formData);
  }
}
