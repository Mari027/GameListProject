import { Component, EventEmitter, Input, Output } from '@angular/core';
import { ApiService } from '../../core/services/api-service';
import { IExternalGameSummary } from '../../core/interfaces/IExternalGameSummary';

@Component({
  selector: 'app-add-game-modal',
  imports: [],
  templateUrl: './add-game-modal.html',
  styleUrl: './add-game-modal.scss',
})
export class AddGameModal {
  @Output() onClose = new EventEmitter<void>();
  @Output() onGameAdded = new EventEmitter<void>();
  @Input() selectedGame: IExternalGameSummary | null = null;

  constructor(private apiService: ApiService){}

  close() {
    this.onClose.emit();
  }

  addGame(){

    this.onGameAdded.emit();
  }
}
