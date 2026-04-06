import { Component, EventEmitter, Output } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators, ɵInternalFormsSharedModule } from "@angular/forms";
import { ApiService } from '../../core/services/api-service';
import { IGameCreation } from '../../core/interfaces/UserGames/IGameCreation';

@Component({
  selector: 'app-game-creation-modal',
  imports: [ReactiveFormsModule],
  templateUrl: './game-creation-modal.html',
  styleUrl: './game-creation-modal.scss',
})
export class GameCreationModal {

  @Output() onClose = new EventEmitter<void>();
  @Output() onGameCreated = new EventEmitter<void>();

  constructor(private apiService: ApiService) { }

  title = new FormControl('', Validators.required);
  description = new FormControl('');
  developer = new FormControl('');
  released = new FormControl('');
  gameStatus = new FormControl('', Validators.required);
  rating = new FormControl('', [Validators.max(5), Validators.min(1)]);
  hoursPlayed = new FormControl('', [Validators.min(0)]);
  startedAt = new FormControl('', []);
  completedAt = new FormControl('', []);
  endedAt = new FormControl('', []);
  review = new FormControl('', [Validators.maxLength(100)])
  errorMsg = '';

  creationForm = new FormGroup({
    title: this.title,
    description: this.description,
    developer: this.developer,
    released: this.released,
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

  createNewGame() {

    //Si formulario NO VÁLIDO, no hacemos nada
    if (this.creationForm.invalid) return;
    //Construimos el objeto Register para el método de apiService
    const gameToCreate: IGameCreation = {
      //! --> Non nullable
      title: this.title.value!,
      description: this.description.value,
      developer: this.developer.value,
      released: this.released.value,
      gameStatus: this.gameStatus.value!,
      rating: Number(this.rating.value),
      hoursPlayed: Number(this.hoursPlayed.value),
      startedAt: this.startedAt.value,
      completedAt: this.completedAt.value,
      review: this.review.value
    };

    //llamada a metodo de api service usando subscribe (una promesa)
    this.apiService.createNewGame(gameToCreate).subscribe({
      //En el caso de que vaya bien
      next: () => {
        this.onGameCreated.emit();
      },
      //En el caso de que vaya mal
      error: () => this.errorMsg = 'Fallo al guardar el juego'
    });
  }

}
