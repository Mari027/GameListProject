import { Component, EventEmitter, OnChanges, Output, SimpleChanges } from '@angular/core';
import { ApiService } from '../../core/services/api-service';
import { ɵInternalFormsSharedModule } from "@angular/forms";

@Component({
  selector: 'app-search-bar',
  imports: [ɵInternalFormsSharedModule],
  templateUrl: './search-bar.html',
  styleUrl: './search-bar.scss',
})
export class SearchBar {


  currentPage = 1;
  size = 8;
  //Para pasarle al catálogo el valor de la búsqueda
  @Output() search = new EventEmitter<string>();

  //Se activa cada vez que el usuario escribe
  onInput(event: Event) {
    //Valor de la búsqueda
    const value = (event.target as HTMLInputElement).value;
    this.search.emit(value);
  }
}
