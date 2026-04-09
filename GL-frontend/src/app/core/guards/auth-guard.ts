import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const authGuard: CanActivateFn = () => {

    //Creamos un router para navegar
    const router = inject(Router);

    //Creamos una constante que guarde el token que se haya recibido
    const token = localStorage.getItem('token');

    //Recogemos el rol del usuario accedido
    const rol = localStorage.getItem('role');

    // Si no hay token, no dejamos pasar
    if (!token) {
        // Si no hay token, redirigimos al login y cancelamos la navegación a la ruta pedida
        router.navigate(['/login']);
        return false;
    };

    //Si el rol es admin la única vista disponible es /admin
    if(rol === 'ADMIN'){
        router.navigate(['/admin']);
        return false;
    }

    return true;
};