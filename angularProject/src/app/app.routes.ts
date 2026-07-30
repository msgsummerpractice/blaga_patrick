import { Routes } from '@angular/router';
import { NotFoundComponent } from './not-found-component/not-found-component';

export const routes: Routes = [
  {
    path: '',
    children: [],
  },
  { path: '**', component: NotFoundComponent },
];
