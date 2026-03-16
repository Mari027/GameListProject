import { inject } from '@angular/core';
import { CanActivateFn, Router } from '@angular/router';

export const adminGuard: CanActivateFn = () => {
  //Creamos un router para navegar
  const router = inject(Router);
  //Creamos una constante que guarde el rol del token que se haya recibido
  const role = localStorage.getItem('role');

  //Si es el admin que continue a la página de admin
  if(role === 'ADMIN') return true;

  /*Si no lo es te manda a la página del catalogo y cancela la navegación 
  a la ruta de admin al devolver falso*/
  router.navigate(['/catalog']);
  return false;
};
