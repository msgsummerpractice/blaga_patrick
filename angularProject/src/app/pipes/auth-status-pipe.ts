import { Pipe, PipeTransform } from '@angular/core';

@Pipe({
  name: 'authStatus',
  standalone: true,
})
export class AuthStatusPipe implements PipeTransform {
  transform(isAuthenticated: boolean): string {
    return isAuthenticated ? 'Authenticated' : 'Not Authenticated';
  }
}
