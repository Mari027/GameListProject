import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { Navbar } from "../../shared/navbar/navbar";
import { ApiService } from '../../core/services/api-service';
import { IUserGame } from '../../core/interfaces/IUserGame';

@Component({
  selector: 'app-game-library',
  imports: [Navbar],
  templateUrl: './game-library.html',
  styleUrl: './game-library.scss',
})
export class GameLibrary implements OnInit{
  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef) { }


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
      },
      error: () => {
        this.errorMsg = 'Error al cargar la lista de juegos';
        this.isLoading = false;
      }
    });
  }
}
