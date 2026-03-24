import { Component, EventEmitter, Input, OnChanges, Output } from '@angular/core';
import { FormControl, Validators, FormGroup, ReactiveFormsModule } from '@angular/forms';
import { IGameRequest } from '../../core/interfaces/IGameRequest';
import { ApiService } from '../../core/services/api-service';
import { IUserGame } from '../../core/interfaces/IUserGame';
import { IUserGameUpdate } from '../../core/interfaces/IUserGameUpdate';

@Component({
  selector: 'app-update-game-modal',
  imports: [ReactiveFormsModule],
  templateUrl: './update-game-modal.html',
  styleUrl: './update-game-modal.scss',
})
export class UpdateGameModal  implements OnChanges{
  @Output() onClose = new EventEmitter<void>();
  @Output() onGameUpdated = new EventEmitter<void>();
  @Input() selectedGame: IUserGame | null = null;

  constructor(private apiService: ApiService) { }


  gameStatus = new FormControl('', Validators.required);
  rating = new FormControl('', [Validators.max(5), Validators.min(1)]);
  hoursPlayed = new FormControl('', [Validators.min(0)]);
  startedAt = new FormControl('');
  completedAt = new FormControl('');
  review = new FormControl('', [Validators.maxLength(100)]);

  errorMsg = '';

  updateForm = new FormGroup({
    gameStatus: this.gameStatus,
    rating: this.rating,
    hoursPlayed: this.hoursPlayed,
    startedAt: this.startedAt,
    completedAt: this.completedAt,
    review: this.review
  })


  close() {
    this.onClose.emit();
  }
  ngOnChanges() {
    if (this.selectedGame) {
      this.updateForm.patchValue({
        gameStatus: this.selectedGame.gameStatus,
        rating: String(this.selectedGame.rating),
        hoursPlayed: String(this.selectedGame.hoursPlayed),
        startedAt: this.selectedGame.startedAt ?? null,
        completedAt: this.selectedGame.completedAt ?? null,
        review: this.selectedGame.review
      });
    }
  }


  updateGame() {

    //Si formulario NO VÁLIDO, no hacemos nada
    if(!this.selectedGame) return;
    if (this.updateForm.invalid) return;
    //Construimos el objeto Register para el método de apiService
    const gameToUpdate: IUserGameUpdate = {
      game: this.selectedGame!.game,
      gameStatus: this.gameStatus.value!,
      rating: Number(this.rating.value),
      hoursPlayed: Number(this.hoursPlayed.value),
      startedAt: this.startedAt.value!,
      completedAt: this.completedAt.value ?? null,
      review: this.review.value ?? null,
    };

    //llamada a metodo de api service usando subscribe (una promesa)
    this.apiService.updateGameFromList(this.selectedGame!.id, gameToUpdate).subscribe({
      //En el caso de que vaya bien
      next: () => {
        alert("Juego Actualizado Correctamente")
        this.onGameUpdated.emit();
      },
      //En el caso de que vaya mal
      error: () => this.errorMsg = 'Fallo al actualizar el juego'
    });
  }
}
