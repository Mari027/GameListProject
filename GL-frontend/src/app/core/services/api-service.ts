import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IExternalGameSummary } from '../interfaces/IExternalGameSummary';
import { ILogin } from '../interfaces/ILogin';
import { IAuthResponse } from '../interfaces/IAuthResponse';
import { IRegister } from '../interfaces/IRegister';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  private apiUrl = 'http://localhost:8080/api/'

  constructor(private http: HttpClient) { }

  //RECORDAR [] porque te devuelve lista
  getCarouselGames(): Observable<IExternalGameSummary[]> {
    return this.http.get<IExternalGameSummary[]>(`${this.apiUrl}external_games/carousel`);
  }

  login(loginRequest: ILogin): Observable<IAuthResponse>{
    //LLamada a la api en POST para login
    return this.http.post<IAuthResponse>(`${this.apiUrl}auth/login`,loginRequest);
  } 

  register(registerRequest: IRegister): Observable<IAuthResponse>{
    return this.http.post<IAuthResponse>(`${this.apiUrl}auth/register`,registerRequest);
  }
}
