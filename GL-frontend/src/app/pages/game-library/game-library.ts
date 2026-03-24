import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Navbar } from "../../shared/navbar/navbar";
import { ApiService } from '../../core/services/api-service';
import { IUserGame } from '../../core/interfaces/IUserGame';
import { UpdateGameModal } from "../../shared/update-game-modal/update-game-modal";

@Component({
  selector: 'app-game-library',
  imports: [Navbar, UpdateGameModal],
  templateUrl: './game-library.html',
  styleUrl: './game-library.scss',
})
export class GameLibrary implements OnInit {
  
  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef) { }

  @Input() selectedGame: IUserGame | null= null;
  isModalVisible = false;

  gameList: IUserGame[] = [];

  isLoading = false;

  errorMsg = '';



  ngOnInit(): void {
    this.chargeGames();
  }


  chargeGames() {
    //Para saber que esta cargando
    this.isLoading = true;
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

  selectGameToUpdate(game: IUserGame){
    this.selectedGame = game;
    this.isModalVisible = true;
  }

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
