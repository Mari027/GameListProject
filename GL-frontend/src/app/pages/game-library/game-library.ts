import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Navbar } from "../../shared/navbar/navbar";
import { ApiService } from '../../core/services/api-service';
import { IUserGame } from '../../core/interfaces/UserGames/IUserGame';
import { UpdateGameModal } from "../../shared/update-game-modal/update-game-modal";
import { GameCreationModal } from "../../shared/game-creation-modal/game-creation-modal";
import { SearchBar } from "../../shared/search-bar/search-bar";
import { Router } from '@angular/router';

@Component({
  selector: 'app-game-library',
  imports: [Navbar, UpdateGameModal, GameCreationModal, SearchBar],
  templateUrl: './game-library.html',
  styleUrl: './game-library.scss',
})
export class GameLibrary implements OnInit {

  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef, private router: Router) { }

  @Input() selectedGame: IUserGame | null = null;
  isUpdateModalVisible = false;
  isCreateModalVisible = false;
  searchValue = '';

  gameList: IUserGame[] = [];

  isLoading = false;

  errorMsg = '';


  ngOnInit(): void {
    this.chargeGames();
  }

  //Inserta el valor de búsqueda en la variable de searchValue
  //Y repite la recarga de los juegos
  onSearchChange(value: string) {

    this.searchValue = value.normalize("NFD")  // separa letras y tildes
      .replace(/[\u0300-\u036f]/g, "")  // elimina las tildes
      .trim()
      .toLowerCase(); //lo pone todo en lowerCase
    this.chargeGames();

  }

  chargeGames() {
    //Para saber que esta cargando
    this.isLoading = true;
    //Método que obtiene la lista de juegos
    this.apiService.getAllUserGames(this.searchValue).subscribe({
      next: (games) => {
        this.gameList = games;
        this.isLoading = false;
        //Obligo a aplicar los cambios que ocurran en el componente
        this.cdr.detectChanges();
        this.isLoading = false;
      },
      error: () => {
        this.errorMsg = 'Error al cargar la lista de juegos';
        this.isLoading = false;
      }
    });
  }

  //Método que nos da el juego seleccionado para pasarselo a update-modal
  selectGameToUpdate(game: IUserGame) {
    this.selectedGame = game;
    this.isUpdateModalVisible = true;
  }

  //Método que se activa al actualizar un juego, desactiva el modal de actualización de la info de un juego y recarga los juegos
  onGameUpdated() {
    this.isUpdateModalVisible = false;
    this.chargeGames();
  }

  //Gestión de la aparición del modal de creación de un juego
  createGame() {
    this.isCreateModalVisible = true;
  }
  onGameCreated() {
    this.isCreateModalVisible = false;
    this.chargeGames();
  }

  //Detalles de un juego
  goToGameDetail(gameId: number, externalId: number | null): void {
    if (externalId) {
      this.router.navigate(['/game', externalId], { state: { source: 'library' } });
    } else {
      this.router.navigate(['/game/custom', gameId], { state: { source: 'library' } });
    }
  }


  //Método de borrado de juegos de la lista
  deleteGameFromList(id: number) {

    const confirmed = confirm("¿Estas seguro de eliminar el juego?");
    if (!confirmed) return;

    this.apiService.deleteGameFromList(id).subscribe({
      next: () => {
        this.chargeGames();
      },
      error: () => alert("No se ha podido eliminar el juego")
    })
  }


  exportCsv() {
    this.apiService.exportCsv().subscribe({
      next: (blob) => {
        // Creamos enlace temporal para la lectura de esos datos binarios
        const url = window.URL.createObjectURL(blob);
        const a = document.createElement('a');
        a.href = url;
        a.download = 'biblioteca.csv';
        a.click();
        window.URL.revokeObjectURL(url);

        //<a>url</a> y hacemos el evento donde se clica mostrandonos el CSV
      },
      error: () => alert('Error al exportar CSV')
    });
  }

  importCsv(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return; //Si no hay nada en el csv se corta

    const file = input.files[0]; //Solo aceptamos el primer archivo
    this.apiService.importCsv(file).subscribe({
      next: (result) => {
        alert(`Importados: ${result.imported}, Omitidos: ${result.skipped}`);
        this.chargeGames();
      },
      error: () => alert('Error al importar')
    });
  }

}
