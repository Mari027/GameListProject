import { HttpErrorResponse, HttpInterceptorFn } from '@angular/common/http';
import { inject } from '@angular/core';
import { Router } from '@angular/router';
import { catchError, throwError } from 'rxjs';

export const authInterceptor: HttpInterceptorFn = (req, next) => {

  const router = inject(Router);
  //Leemos el token guardado en LOCAL STORAGE
  const token = localStorage.getItem('token');

  //Si no hay token al hacer LOGIN/REGISTER, dejamos pasar la petición sin modificarla
  if(!token){
    return next(req);
  }
  
  //SI hay token, clono la petición y le añado al header el token correspondiente
  const requestWithToken = req.clone({
    setHeaders:{
      Authorization:`Bearer ${token}`
    }
  })
  return next(requestWithToken).pipe(
        catchError((error: HttpErrorResponse) => {
            // Enviamos al usuario con token expirado al login y limpiamos la memoria
            if (error.status === 401) {
                localStorage.removeItem('token');
                localStorage.removeItem('role');
                localStorage.removeItem('email');
                localStorage.removeItem('nickname');
                router.navigate(['/login']);
            }
            return throwError(() => error);
        })
    );
};
