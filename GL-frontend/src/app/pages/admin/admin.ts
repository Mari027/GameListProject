import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ApiService } from '../../core/services/api-service';
import { IUserResponse } from '../../core/interfaces/UserGames/IUserResponse';

@Component({
  selector: 'app-admin',
  imports: [],
  templateUrl: './admin.html',
  styleUrl: './admin.scss',
})
export class Admin implements OnInit {

  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef) { }


  users: IUserResponse[] = [];
  isLoading = true;

  ngOnInit(): void {
    this.chargeUser();
  }

  chargeUser() {
    return this.apiService.getAllUsers().subscribe({
      next: (userList) => {
        this.users = userList;
        this.isLoading = false;
        this.cdr.detectChanges();
      },
      error: () => console.log("No es posible obtener la lista de usuarios")
    })
  }

  deleteUser(id: number){
    this.apiService.deleteUser(id).subscribe({
      next: () => alert("Usuario Eliminado Correctamente"),
      error: () => alert("No se ha podido eliminar el usuario")
    })
  }
}
