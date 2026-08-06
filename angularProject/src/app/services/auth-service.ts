import { inject, Injectable } from '@angular/core';
import { AuthResponse } from '../interfaces/auth-respons';
import { Observable } from 'rxjs/internal/Observable';
import { tap } from 'rxjs/operators';
import { HttpClient } from '@angular/common/http';
import { MfaResponse } from '../interfaces/mfa-reponse';

@Injectable({ providedIn: 'root' })
export class AuthService {
  private authenticated: boolean = true;
  private mockToken: string = 'mock-token';
  private api_url: string =
    'https://patrickblaga-backend.ambitiouspebble-f84047ca.westeurope.azurecontainerapps.io/auth';
  private http = inject(HttpClient);

  isAuthenticated(): boolean {
    return !!this.getToken();
  }

  login(username: string, password: string): Observable<AuthResponse> {
    return this.http.post<AuthResponse>(this.api_url + '/login', { username, password });
  }

  verifyMfa(username: string, otp: string): Observable<MfaResponse> {
    return this.http.post<MfaResponse>(this.api_url + '/verify-mfa', { username, otp }).pipe(
      tap((response: MfaResponse) => {
        if (response.token) {
          localStorage.setItem('token', response.token);
        }
      }),
    );
  }

  logout(): void {
    localStorage.removeItem('token');
  }

  getToken(): string | null {
    return localStorage.getItem('token');
  }
}
