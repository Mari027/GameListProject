import { Routes } from '@angular/router';
import { Login } from './pages/auth/login/login';
import { Register } from './pages/auth/register/register';
import { Catalog } from './pages/catalog/catalog';
import { authGuard } from './core/guards/auth-guard';
import { Admin } from './pages/admin/admin';
import { adminGuard } from './core/guards/admin-guard';
import { GameLibrary } from './pages/game-library/game-library';
import { GameDetail } from './pages/game-detail/game-detail';

export const routes: Routes = [
    {
        path: '',
        redirectTo: 'login',
        pathMatch: 'full'
    },
    {
        path: 'login',
        component: Login
    },
    {
        path: 'register',
        component: Register
    },
    {
        path: 'catalog',
        component: Catalog,
        canActivate: [authGuard]
    },
    {
        path: 'admin',
        component: Admin,
        canActivate: [adminGuard]
    },
    {
        path: 'library',
        component: GameLibrary,
        canActivate: [authGuard]
    },
    {
        path: 'game/:id',
        component: GameDetail,
        canActivate: [authGuard]
    },
    {
        path: 'game/custom/:id',
        component: GameDetail,
        canActivate: [authGuard]
    }
];
