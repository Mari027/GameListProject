import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../../../core/services/api-service';
import { ILogin } from '../../../../core/interfaces/ILogin';
import { Router } from '@angular/router';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login {

  constructor(private apiService: ApiService, private router: Router) { }

  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  errorMsg ='';

  loginForm = new FormGroup({
    email: this.email,
    password: this.password
  })


  onSubmit() {
    //Si formulario NO VÁLIDO, no hacemos nada
    if (this.loginForm.invalid) return;
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
        localStorage.setItem('token',response.token);
        localStorage.setItem('role',response.role);
        console.log("Login correcto", response);
        if(response.role === 'ADMIN'){
          this.router.navigate(['/admin']);
        }else{
          this.router.navigate(['/catalog']);
        }
      },
      //En el caso de que vaya mal
      error: (response) => this.errorMsg = 'Email o contraseña incorrectos'
    });
  }
}
