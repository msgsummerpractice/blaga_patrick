import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private authenticated: boolean = true;
  private mockToken: string = 'mock-token';

  isAuthenticated(): boolean {
    return this.authenticated;
  }

  login(): void {
    this.authenticated = true;
  }

  logout(): void {
    this.authenticated = false;
  }

  getToken(): string | null {
    return this.authenticated ? this.mockToken : null;
  }
}
