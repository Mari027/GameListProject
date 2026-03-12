import { HttpInterceptorFn } from '@angular/common/http';

export const authInterceptor: HttpInterceptorFn = (req, next) => {
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
  return next(requestWithToken);
};
