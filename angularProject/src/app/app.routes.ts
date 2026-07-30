import { Routes } from '@angular/router';
import { NotFoundComponent } from './components/not-found-component/not-found-component';
import { LogionComponent } from './components/login-component/logion-component';
import { HomeComponent } from './components/home-component/home-component';

export const routes: Routes = [
  { path: '', component: HomeComponent },
  {
    path: 'login',
    loadComponent: () =>
      import('./components/login-component/logion-component').then((m) => m.LogionComponent),
  },

  { path: '**', component: NotFoundComponent },
];
