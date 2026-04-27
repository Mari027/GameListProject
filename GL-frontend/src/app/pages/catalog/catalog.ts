import { Component, Input, OnInit } from '@angular/core';
import { Navbar } from "../../shared/navbar/navbar";
import { ApiService } from '../../core/services/api-service';
import { IExternalGameSummary } from '../../core/interfaces/ExternalGame/IExternalGameSummary';
import { ChangeDetectorRef } from '@angular/core';
import { AddGameModal } from '../../shared/add-game-modal/add-game-modal';
import { SearchBar } from "../../shared/search-bar/search-bar";

@Component({
  selector: 'app-catalog',
  imports: [Navbar, AddGameModal, SearchBar],
  templateUrl: './catalog.html',
  styleUrl: './catalog.scss',
})
export class Catalog implements OnInit {

  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef) { }


  gameList: IExternalGameSummary[] = [];
  //nulo mientras no se clique añadir juego
  //Input pasa la información al componente hijo
  @Input() selectedGame: IExternalGameSummary | null = null;
  currentPage = 1;
  size = 6;
  searchValue = '';

  isLoading = false;
  isModalVisible = false;

  errorMsg = '';


  //Implemento onInit para que se haga la llamada en la creación del componente
  ngOnInit(): void {
    this.chargeGames();
  }

  //Pasar a la pág siguiente
  nextPage() {
    this.currentPage++;
    this.chargeGames();
  }
  //Pasar a la pág anterior
  previousPage() {
    console.log('previousPage llamado, página actual:', this.currentPage);
    if (this.currentPage === 1) return;
    this.currentPage--;
    this.chargeGames();
  }

  //Método para pasar el juego que vamos a añadir al hijo (modal de añadir juego)
  selectGameToAdd(game: IExternalGameSummary) {
    this.selectedGame = game;
    this.isModalVisible = true;
  }

  //Inserta el valor de búsqueda en la variable de searchValue
  //Y repite la recarga de los juegos
  onSearchChange(value: string) {

    this.searchValue = value.normalize("NFD")  // separa letras y tildes
      .replace(/[\u0300-\u036f]/g, "")  // elimina las tildes
      .trim()
      .toLowerCase(); //lo pone todo en lowerCase
    //Reseteamos la página para que no salgan los resultados desde la primera pág
    this.currentPage = 1;
    this.chargeGames();

  }

  chargeGames() {
    //Para saber que esta cargando
    this.isLoading = true;
    this.apiService.getAllgames(this.currentPage, this.size, this.searchValue).subscribe({
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
