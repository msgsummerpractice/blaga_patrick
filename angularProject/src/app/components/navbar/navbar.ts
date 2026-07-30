import { Component, inject } from '@angular/core';
import { AuthService } from '../../services/auth-service';
import { MatToolbar } from '@angular/material/toolbar';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { RouterLink } from '@angular/router';
import { AuthStatusPipe } from '../../pipes/auth-status-pipe';

@Component({
  selector: 'app-navbar',
  imports: [MatToolbar, MatButton, MatIcon, RouterLink, AuthStatusPipe],
  templateUrl: './navbar.html',
  styleUrls: ['./navbar.css'],
})
export class Navbar {
  private authService: AuthService = inject(AuthService);
  loggedIn: boolean = this.authService.isAuthenticated();
}
