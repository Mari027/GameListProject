import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../../core/services/api-service';
import { ILogin } from '../../../core/interfaces/Auth/ILogin';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  constructor(private apiService: ApiService, private router: Router) { }

  //Expresión regular para un patrón de email correcto, ya que .email solo detecta @algo sin el .com .es etc
  email = new FormControl('', [Validators.required, Validators.email, Validators.pattern("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,4}$")]);
  password = new FormControl('', [Validators.required, Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.@#$!%*?&])[A-Za-z\\d.@#$!%*?&]+$")]);
  errorMsg = '';

  loginForm = new FormGroup({
    email: this.email,
    password: this.password
  })

  onSubmit() {
    //Si formulario NO VÁLIDO, no hacemos nada
    if (this.loginForm.invalid) return;

    this.errorMsg = '';

    //Construimos el objeto Login para el método de apiService
    const loginRequest: ILogin = {
      //! --> Non nullable
      email: this.email.value!,
      password: this.password.value!
    };

    //llamada a metodo de api service usando subscribe (una promesa)
    this.apiService.login(loginRequest).subscribe({
      //En el caso de que vaya bien
      next: (response) => {
        //Guardamos el token de usuario y su rol en LOCAL STORAGE
        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);
        localStorage.setItem('email', response.email);
        localStorage.setItem('nickname', response.username);
        console.log("Login correcto", response);
        if (response.role === 'ADMIN') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/catalog']);
        }
      },
      //En el caso de que vaya mal
      error: (err) => {
        //Según el estado de error que nos llegue lanzamos un mensaje u otro
        const status = err.status;
            if (status === 401 || status === 403) {
                this.errorMsg = 'Email o contraseña incorrectos';
            } else if (status === 404) {
                this.errorMsg = 'No existe una cuenta con el email introducido';
            } else {
                this.errorMsg = 'Error al iniciar sesión. Inténtalo de nuevo';
            }
        this.loginForm.get('password')?.reset();
      }
    });
  }
}
