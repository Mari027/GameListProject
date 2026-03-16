import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = () => {
    //Creamos un router para navegar
    const router = inject(Router);
    //Creamos una constante que guarde el token que se haya recibido
    const token = localStorage.getItem('token');

    // Si hay token, dejamos pasar
    if (token) return true;

    // Si no hay token, redirigimos al login y cancelamos la navegación a la ruta pedida
    router.navigate(['/login']);
    return false;
};