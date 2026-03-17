import { Component, Input, OnInit } from '@angular/core';
import { Navbar } from "../../shared/navbar/navbar";
import { ApiService } from '../../core/services/api-service';
import { IExternalGameSummary } from '../../core/interfaces/IExternalGameSummary';
import { ChangeDetectorRef } from '@angular/core';
import { AddGameModal } from '../../shared/add-game-modal/add-game-modal';

@Component({
  selector: 'app-catalog',
  imports: [Navbar,AddGameModal],
  templateUrl: './catalog.html',
  styleUrl: './catalog.scss',
})
export class Catalog implements OnInit {

  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef) { }


  gameList: IExternalGameSummary[] = [];
  //nulo mientras no se clique añadir juego
  @Input() selectedGame: IExternalGameSummary | null= null;
  currentPage = 1;
  size = 8;

  isLoading = false;
  isModalVisible = false;

  errorMsg = '';



  ngOnInit(): void {
    this.chargeGames();
  }

  nextPage() {
    this.currentPage++;
    this.chargeGames();
  }
  
  previousPage() {
    console.log('previousPage llamado, página actual:', this.currentPage);
    if (this.currentPage === 1) return;
    this.currentPage--;
    this.chargeGames();
  }

  selectGameToAdd(game: IExternalGameSummary){
    this.selectedGame = game;
    this.isModalVisible = true;
  }

  chargeGames() {
    //Para saber que esta cargando
    this.isLoading = true;
    this.apiService.getAllgames(this.currentPage, this.size).subscribe({
      next: (games) => {
        this.gameList = games;
        this.isLoading = false;
        //Obligo a aplicar los cambios que ocurran en el componente
        this.cdr.detectChanges();
      },
      error: () => {
        this.errorMsg = 'Error al cargar la lista de juegos';
        this.isLoading = false;
      }
    });
  }
}
