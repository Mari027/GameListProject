import { AsyncPipe } from '@angular/common';
import { Component, OnInit } from '@angular/core';
import { ApiService } from '../../../../core/service/api-service';
import { map, Observable } from 'rxjs';
import { IExternalGameSummary } from '../../../../core/interface/IExternalGameSummary';

@Component({
  selector: 'app-carousel',
  imports: [AsyncPipe],
  templateUrl: './carousel.html',
  styleUrl: './carousel.scss',
})
export class Carousel implements OnInit {

  public carousel$!: Observable<IExternalGameSummary[]>
  
  constructor(private apiService: ApiService) { }

  ngOnInit(): void {
    //Mapeo de objeto a Array
    this.carousel$ = this.apiService.getCarouselGames().pipe(
       map(result => Array.isArray(result) ? result : [result]));

  }
}
