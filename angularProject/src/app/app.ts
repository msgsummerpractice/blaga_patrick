import { Component, signal } from '@angular/core';
import { MatButton } from '@angular/material/button';
import { MatIcon } from '@angular/material/icon';
import { Navbar } from './navbar/navbar';
import { Card } from './card/card';
@Component({
  selector: 'app-root',
  imports: [MatButton, MatIcon, Navbar, Card],
  templateUrl: './app.html',
  styleUrls: ['./app.css'],
})
export class App {
  protected readonly title = signal('angularProject');
}
