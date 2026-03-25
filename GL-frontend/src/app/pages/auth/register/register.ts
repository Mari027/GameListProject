import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { ApiService } from '../../../core/services/api-service';
import { Router } from '@angular/router';
import { IRegister } from '../../../core/interfaces/IRegister';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {

  constructor(private apiService: ApiService, private router: Router) { }

  nickname = new FormControl('', Validators.required);
  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  passwordConfirm = new FormControl('', [Validators.required]);
  errorMsg = '';

  registerForm = new FormGroup({
    username: this.nickname,
    email: this.email,
    password: this.password,
    passwordConfirm: this.passwordConfirm
  })

  onSubmit() {
    //Verifico contraseña
    if(this.password.value !==  this.passwordConfirm.value){
      this.errorMsg='Contraseñas no coincidentes'
      return;
    }
    //Si formulario NO VÁLIDO, no hacemos nada
    if (this.registerForm.invalid) return;
    //Construimos el objeto Register para el método de apiService
    const registerRequest: IRegister = {
      //! --> Non nullable
      nickname: this.nickname.value!,
      email: this.email.value!,
      password: this.password.value!
    };

    //llamada a metodo de api service usando subscribe (una promesa)
    this.apiService.register(registerRequest).subscribe({
      //En el caso de que vaya bien
      next: (response) => {
        //Guardamos el token de usuario y su rol en LOCAL STORAGE
        localStorage.setItem('token', response.token);
        localStorage.setItem('role', response.role);
        console.log("Login correcto", response);
        if (response.role === 'ADMIN') {
          this.router.navigate(['/admin']);
        } else {
          this.router.navigate(['/catalog']);
        }
      },
      //En el caso de que vaya mal
      error: (response) => this.errorMsg = 'Email o contraseña incorrectos'
    });
  }
}

