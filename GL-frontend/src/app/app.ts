import { Component, signal } from '@angular/core';
import { RouterOutlet } from '@angular/router';
import { Carousel } from "./pages/login/components/carousel/carousel";

@Component({
  selector: 'app-root',
  imports: [RouterOutlet, Carousel],
  templateUrl: './app.html',
  styleUrl: './app.scss'
})
export class App {
  protected readonly title = signal('GL-frontend');
}
