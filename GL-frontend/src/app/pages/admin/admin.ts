import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { ApiService } from '../../core/services/api-service';
import { IUserResponse } from '../../core/interfaces/User/IUserResponse';
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
  currentEmail = localStorage.getItem('email') ?? '';

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

    const confirmed = confirm('¿Estás seguro de eliminar este usuario?');
    if (!confirmed) return;

    this.apiService.deleteUser(id).subscribe({
      next: () => {
        alert("Usuario Eliminado Correctamente")
        this.chargeUser();
      },
      error: () => alert("No se ha podido eliminar el usuario")
    })
  }

  // Devuelve true si el botón debe estar deshabilitado
  cannotDelete(user: IUserResponse): boolean {
    return user.email === this.currentEmail || user.role === 'ADMIN';
  }

  logout() {
    localStorage.removeItem('token');
    localStorage.removeItem('role');
    localStorage.removeItem('email');
    localStorage.removeItem('nickname');
    this.router.navigate(['/login']);
  }
}
