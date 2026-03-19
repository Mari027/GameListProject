import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ApiService } from '../../core/services/api-service';
import { IExternalGameSummary } from '../../core/interfaces/IExternalGameSummary';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { IGameRequest } from '../../core/interfaces/IGameRequest';

@Component({
  selector: 'app-add-game-modal',
  imports: [ReactiveFormsModule],
  templateUrl: './add-game-modal.html',
  styleUrl: './add-game-modal.scss',
})
export class AddGameModal {
  @Output() onClose = new EventEmitter<void>();
  @Output() onGameAdded = new EventEmitter<void>();
  @Input() selectedGame: IExternalGameSummary | null = null;

  constructor(private apiService: ApiService) { }


  gameStatus = new FormControl('', Validators.required);
  rating = new FormControl('', [Validators.max(5), Validators.min(1)]);
  hoursPlayed = new FormControl('', [Validators.min(0)]);
  startedAt = new FormControl('', []);
  completedAt = new FormControl('', []);
  endedAt = new FormControl('', []);
  review = new FormControl('', [Validators.maxLength(100)])
  errorMsg = '';

  registerForm = new FormGroup({
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

  addGame() {
    
    //Si formulario NO VÁLIDO, no hacemos nada
    if (this.registerForm.invalid) return;
    //Construimos el objeto Register para el método de apiService
    const gameToAdd: IGameRequest = {
      //! --> Non nullable
      externalGameId: this.selectedGame!.id,
      gameStatus: this.gameStatus.value!,
      rating: Number(this.rating.value),
      hoursPlayed: Number(this.hoursPlayed.value),
      startedAt: this.startedAt.value,
      completedAt: this.completedAt.value,
      review: this.review.value
    };

    //llamada a metodo de api service usando subscribe (una promesa)
    this.apiService.addGameToList(gameToAdd).subscribe({
      //En el caso de que vaya bien
      next: (response) => {
        this.onGameAdded.emit();
      },
      //En el caso de que vaya mal
      error: (response) => this.errorMsg = 'Fallo al guardar el juego'
    });
  }
}

