import { ChangeDetectorRef, Component, OnInit } from '@angular/core';
import { FormControl, FormGroup, ReactiveFormsModule, Validators } from '@angular/forms';
import { ApiService } from '../../../core/services/api-service';
import { ILogin } from '../../../core/interfaces/Auth/ILogin';
import { Router } from '@angular/router';
import { IExternalGameSummary } from '../../../core/interfaces/ExternalGame/IExternalGameSummary';

@Component({
  selector: 'app-login',
  imports: [ReactiveFormsModule],
  templateUrl: './login.html',
  styleUrl: './login.scss',
})
export class Login implements OnInit {

  constructor(private apiService: ApiService, private router: Router, private cdr: ChangeDetectorRef) { }

  email = new FormControl('', [Validators.required, Validators.email]);
  password = new FormControl('', [Validators.required]);
  errorMsg = '';
  imagenFondo: string | null = null;

  loginForm = new FormGroup({
    email: this.email,
    password: this.password
  })

  ngOnInit(): void {
    this.obtenerIMG();
  }


  obtenerIMG() {
    this.apiService.getCarouselGames().subscribe({
      next: (imgs) => {
        console.log("IMAGENES:", imgs); // ← comprueba aquí si hay datos
        this.imagenFondo = imgs[1]?.backgroundImage ?? null;
        this.cdr.detectChanges();
      },
      error: () => console.log("No se puede cargar las imagenes")
    })
  }


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
