import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ApiService } from '../../core/services/api-service';
import { IUserResponse } from '../../core/interfaces/UserGames/IUserResponse';
import { Router } from '@angular/router';

@Component({
  selector: 'app-admin',
  imports: [],
  templateUrl: './admin.html',
  styleUrl: './admin.scss',
})
export class Admin implements OnInit {

  constructor(private apiService: ApiService, private cdr: ChangeDetectorRef, private router: Router) { }


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

  deleteUser(id: number) {
    this.apiService.deleteUser(id).subscribe({
      next: () => {
        alert("Usuario Eliminado Correctamente")
        this.chargeUser();
      },
      error: () => alert("No se ha podido eliminar el usuario")
    })
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    this.router.navigate(['/login']);
  }
}
