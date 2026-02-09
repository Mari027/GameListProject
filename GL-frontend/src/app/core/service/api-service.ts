import { HttpClient } from '@angular/common/http';
import { Injectable } from '@angular/core';
import { Observable } from 'rxjs';
import { IExternalGameSummary } from '../interface/IExternalGameSummary';

@Injectable({
  providedIn: 'root',
})
export class ApiService {

  private apiUrl = 'http://localhost:8080/api/external_games'

  constructor(private http: HttpClient) { }

  //RECORDAR [] porque te devuelve lista
  getCarouselGames(): Observable<IExternalGameSummary[]> {
    return this.http.get<IExternalGameSummary[]>(`${this.apiUrl}/carousel`);
  }
}
