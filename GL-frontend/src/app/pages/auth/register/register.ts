import { Component } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from "@angular/forms";
import { ApiService } from '../../../core/services/api-service';
import { Router } from '@angular/router';
import { IRegister } from '../../../core/interfaces/Auth/IRegister';

@Component({
  selector: 'app-register',
  imports: [ReactiveFormsModule],
  templateUrl: './register.html',
  styleUrl: './register.scss',
})
export class Register {

  constructor(private apiService: ApiService, private router: Router) { }

  errorMsg = '';

  nickname = new FormControl('', [
    Validators.required,
    Validators.minLength(3),
    Validators.maxLength(20)
  ]);

  email = new FormControl('', [
    Validators.required,
    Validators.email,
    Validators.pattern("^[a-zA-Z0-9._%+-]+@[a-zA-Z0-9.-]+\\.[a-z]{2,4}$")]);

  password = new FormControl('', [
    Validators.required,
    Validators.minLength(8),
    //Numeros, mayus, minus y caracter especial
    Validators.pattern("^(?=.*[a-z])(?=.*[A-Z])(?=.*\\d)(?=.*[.@#$!%*?&])[A-Za-z\\d.@#$!%*?&]+$")]);

  passwordConfirm = new FormControl('', [Validators.required]);



  registerForm = new FormGroup({
    username: this.nickname,
    email: this.email,
    password: this.password,
    passwordConfirm: this.passwordConfirm
  })

  onSubmit() {
    //Verifico contraseña
    if (this.password.value !== this.passwordConfirm.value) {
      this.errorMsg = 'Contraseñas no coincidentes'
      return;
    }
    //Si formulario NO VÁLIDO, no hacemos nada
    if (this.registerForm.invalid) return;

    this.errorMsg = '';

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
        const message = err.error?.message ?? '';
        const status = err.status;
        if (message.toLowerCase().includes('email') && status === 409) {
          this.errorMsg = 'Este email ya está registrado';
        } else if ( message.toLowerCase().includes('nickname') && status === 409) {
          this.errorMsg = 'Este nombre de usuario ya está en uso';
        } else {
          this.errorMsg = 'Error al registrarse. Inténtalo de nuevo';
        }
      }
    });
  }
}

