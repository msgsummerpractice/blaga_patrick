import { Routes } from '@angular/router';
import { NotFoundComponent } from './not-found-component/not-found-component';
import { LogionComponent } from './login-component/logion-component';
import { HomeComponent } from './home-component/home-component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'login',
    loadComponent: () =>
      import('./login-component/logion-component').then((m) => m.LogionComponent),
  },

  { path: '**', component: NotFoundComponent },
];
