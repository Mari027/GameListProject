import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Navbar } from "../../shared/navbar/navbar";
import { ApiService } from '../../core/services/api-service';
import { IUserGame } from '../../core/interfaces/UserGames/IUserGame';
import { UpdateGameModal } from "../../shared/update-game-modal/update-game-modal";
import { GameCreationModal } from "../../shared/game-creation-modal/game-creation-modal";

@Component({
  selector: 'app-game-library',
  imports: [Navbar, UpdateGameModal, GameCreationModal],
  templateUrl: './game-library.html',
  styleUrl: './game-library.scss',
})
export class GameLibrary implements OnInit {
  
  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef) { }

  @Input() selectedGame: IUserGame | null= null;
  isUpdateModalVisible = false;
  isCreateModalVisible = false;

  gameList: IUserGame[] = [];

  isLoading = false;

  errorMsg = '';


  ngOnInit(): void {
    this.chargeGames();
  }


  chargeGames() {
    //Para saber que esta cargando
    this.isLoading = true;
    //Método que obtiene la lista de juegos
    this.apiService.getAllUserGames().subscribe({
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
  selectGameToUpdate(game: IUserGame){
    this.selectedGame = game;
    this.isUpdateModalVisible = true;
  }

  //Gestión de la aparición del modal de creación de un juego
  createGame(){
    this.isCreateModalVisible = true;
  }
  onGameCreated(){
    this.isCreateModalVisible = false;
    this.chargeGames();
  }

  //Método que se activa al actualizar un juego, desactiva el modal de actualización de la info de un juego y recarga los juegos
  onGameUpdated(){
    this.isUpdateModalVisible = false;
    this.chargeGames();
  }

  //Método de borrado de juegos de la lista
  deleteGameFromList(id: number) {
    this.apiService.deleteGameFromList(id).subscribe({
      next: () => {
        confirm("¿Estas seguro de eliminar el juego?");
        this.chargeGames();
        
      },
      error: () => alert("No se ha podido eliminar el juego")
    })
  }

}
