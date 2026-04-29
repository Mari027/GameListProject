import { ChangeDetectorRef, Component, Input, OnInit } from '@angular/core';
import { ActivatedRoute, Router } from '@angular/router';
import { ApiService } from '../../core/services/api-service';
import { IExternalGame } from '../../core/interfaces/ExternalGame/IExternalGame';
import { IUserGame } from '../../core/interfaces/UserGames/IUserGame';
import { Navbar } from '../../shared/navbar/navbar';
import { CommonModule } from '@angular/common';
import { IExternalGameSummary } from '../../core/interfaces/ExternalGame/IExternalGameSummary';
import { AddGameModal } from "../../shared/add-game-modal/add-game-modal";
import { IGameResponseDTO } from '../../core/interfaces/Games/IGameResponseDTO';

@Component({
    selector: 'app-game-detail',
    imports: [CommonModule, AddGameModal],
    templateUrl: './game-detail.html',
    styleUrl: './game-detail.scss'
})
export class GameDetail implements OnInit {

    constructor(private route: ActivatedRoute, private router: Router, private apiService: ApiService, private cdr: ChangeDetectorRef) { }

    //nulo mientras no se clique añadir juego
    //Input pasa la información al componente hijo
    @Input() selectedGame: IExternalGameSummary | null = null;
    game: IExternalGame | null = null;
    customGame: IGameResponseDTO | null = null;
    userGame: IUserGame | null = null;
    isCustom: boolean = false;
    isLoading = true;
    isModalVisible = false;
    errorMsg = '';
    //Variable que nos indicará si estamos en catálogo o libreria
    source: string = 'catalog';

    ngOnInit(): void {
        //Buscamos de donde venimos libreria / catálogo
        this.source = history.state?.source ?? 'catalog';
        //Si es custom, redireccionamos el enlace
        this.isCustom = this.route.snapshot.routeConfig?.path?.includes('custom') ?? false;
        //Añadimos el id del juego
        const id = Number(this.route.snapshot.paramMap.get('id'));

        if (this.isCustom) {
            this.apiService.getCustomGameById(id).subscribe({
                next: (game) => {
                    this.customGame = game;
                    this.isLoading = false;
                    if (this.source === 'library') {
                        this.loadUserGameDataByInternalId(id);
                    }
                    this.cdr.detectChanges();
                },
                error: () => {
                    this.errorMsg = 'Error al cargar el juego';
                    this.isLoading = false;
                }
            });
        } else {
            // Juego externo
            this.apiService.getGameById(id).subscribe({
                next: (game) => {
                    this.game = game;
                    this.isLoading = false;
                    if (this.source === 'library') {
                        this.loadUserGameData(id);
                    }
                    this.cdr.detectChanges();
                },
                error: () => {
                    this.errorMsg = 'Error al cargar el juego';
                    this.isLoading = false;
                }
            });
        }
    }

    // Busca los datos del usuario para juegos externos
    loadUserGameData(gameId: number): void {
        this.apiService.getAllUserGames().subscribe({
            next: (games) => {
                this.userGame = games.find(g => g.game.externalId === gameId) ?? null;
                this.isLoading = false;
                this.cdr.detectChanges();
            },
            error: () => { this.isLoading = false; }
        });
    }

    // Busca los datos del usuario para juegos internos
    loadUserGameDataByInternalId(gameId: number): void {
        this.apiService.getAllUserGames().subscribe({
            next: (games) => {
                this.userGame = games.find(g => g.game.id === gameId) ?? null;
                this.isLoading = false;
                this.cdr.detectChanges();
            },
            error: () => { this.isLoading = false; }
        });
    }

    //Método para pasar el juego que vamos a añadir al hijo (modal de añadir juego)
    selectGameToAdd(game: IExternalGameSummary) {
        this.selectedGame = game;
        this.isModalVisible = true;
    }
    //Volvemos a la página desde la que partimos
    return(): void {
        this.router.navigate([`/${this.source}`]);
    }
}