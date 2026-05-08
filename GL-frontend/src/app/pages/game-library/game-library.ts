import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Navbar } from "../../shared/navbar/navbar";
import { ApiService } from '../../core/services/api-service';
import { IUserGame } from '../../core/interfaces/UserGames/IUserGame';
import { UpdateGameModal } from "../../shared/update-game-modal/update-game-modal";
import { GameCreationModal } from "../../shared/game-creation-modal/game-creation-modal";
import { SearchBar } from "../../shared/search-bar/search-bar";
import { Router } from '@angular/router';
import { ConfirmModal } from "../../shared/confirm-modal/confirm-modal";
import { ToastrService } from 'ngx-toastr';

@Component({
  selector: 'app-game-library',
  imports: [Navbar, UpdateGameModal, SearchBar, ConfirmModal],
  templateUrl: './game-library.html',
  styleUrl: './game-library.scss',
})
export class GameLibrary implements OnInit {

  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef, private router: Router, 
    private toastr: ToastrService) { }

  @Input() selectedGame: IUserGame | null = null;
  isUpdateModalVisible = false;
  searchValue = '';
  selectedStatus: string = '';

  gameList: IUserGame[] = [];

  isLoading = false;

  //Modal de confirmación para el borrado de juegos
  showConfirmDelete: boolean = false;
  gameToDelete: number | null = null;

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

  //Guardamos el filtro y recargamos juegos con el filtro aplicado
  onStatusFilter(event: Event) {
    this.selectedStatus = (event.target as HTMLSelectElement).value;
    this.chargeGames();
  }

  chargeGames() {
    //Para saber que esta cargando
    this.isLoading = true;
    //Método que obtiene la lista de juegos
    this.apiService.getAllUserGames(this.searchValue, this.selectedStatus).subscribe({
      next: (games) => {
        this.gameList = games;
        this.isLoading = false;
        //Obligo a aplicar los cambios que ocurran en el componente
        this.cdr.detectChanges();
        this.isLoading = false;
      },
      error: () => {
        this.errorMsg = 'Error al cargar la biblioteca';
        this.toastr.error('Error al cargar la biblioteca')
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


  //Detalles de un juego
  goToGameDetail(gameId: number, externalId: number | null): void {
    if (externalId) {
      this.router.navigate(['/game', externalId], { state: { source: 'library' } });
    } else {
      this.router.navigate(['/game/custom', gameId], { state: { source: 'library' } });
    }
  }

  //Pedimos borrar y el juego a borrar
  requestDelete(id: number) {
    this.showConfirmDelete = true;
    this.gameToDelete = id;
  }
  //Cancelamos borrar
  cancelDelete() {
    this.showConfirmDelete = false;
  }

  //Método de borrado de juegos de la lista
  //Solo si se acepta el borrado
  deleteGameFromList() {

    if (this.gameToDelete === null) return;

    this.apiService.deleteGameFromList(this.gameToDelete).subscribe({
      next: () => {
        this.showConfirmDelete = false;
        this.gameToDelete = null;
        this.toastr.success("Juego borrado correctamente",'Éxito')
        this.chargeGames();
      },
      error: () => this.toastr.error("No se ha podido eliminar el juego",'Error')
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
        window.URL.revokeObjectURL(url);//<a>url</a> y hacemos el evento donde se clica mostrandonos el CSV
        this.toastr.success('Biblioteca exportada correctamente','Éxito')
      },
      error: () => this.toastr.error('Error al exportar CSV','Error')
    });
  }

  importCsv(event: Event) {
    const input = event.target as HTMLInputElement;
    if (!input.files?.length) return; //Si no hay nada en el csv se corta

    const file = input.files[0]; //Solo aceptamos el primer archivo
    this.apiService.importCsv(file).subscribe({
      next: (result) => {
        this.toastr.info(`Importados: ${result.imported}, Omitidos: ${result.skipped}`, 'Importación completada');
        input.value = '';
        this.chargeGames();
      },
      error: () => this.toastr.error('Error al importar CSV','Error')
    });
  }

}
