import { Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { Navbar } from './navbar/navbar';
import { Card } from './card/card';
import { RequireAuth } from './require-auth';
import { RouterOutlet } from '@angular/router';
@Component({
  selector: 'app-root',
  imports: [MatButton, MatIcon, Navbar, Card, RequireAuth, RouterOutlet],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {
  protected readonly title = signal('angularProject');
}
