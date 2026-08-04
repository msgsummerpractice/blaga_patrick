import { Component, signal } from '@angular/core';
import { AuthService } from './services/auth-service';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { Navbar } from './components/navbar/navbar';
import { Card } from './components/card/card';
import { RequireAuth } from './require-auth';
import { RouterOutlet } from '@angular/router';
import { AuthStatusPipe } from './pipes/auth-status-pipe';
@Component({
  selector: 'app-root',
  imports: [MatButton, MatIcon, Navbar, Card, RequireAuth, RouterOutlet, AuthStatusPipe],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {
  constructor(public authService: AuthService) {}
  protected readonly title = signal('angularProject');
}
