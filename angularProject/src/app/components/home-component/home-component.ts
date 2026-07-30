import { Component, OnDestroy, OnInit } from '@angular/core';
import { Card } from '../card/card';
import { MatButtonModule } from '@angular/material/button';
import { RouterLink } from '@angular/router';

@Component({
  selector: 'app-home-component',
  imports: [Card, MatButtonModule, RouterLink],
  templateUrl: './home-component.html',
  styleUrls: ['./home-component.css'],
})
export class HomeComponent implements OnInit, OnDestroy {
  private intervalId: any;
  private colors: string[] = ['#FF5733', '#33FF57', '#3357FF', '#F333FF', '#33FFF5'];
  ngOnInit(): void {
    const elem = document.getElementById('idDog');
    this.intervalId = setInterval(() => {
      const randomColor = this.colors[Math.floor(Math.random() * this.colors.length)];
      if (elem) {
        elem.style.color = randomColor;
      }
    }, 1000);
  }

  ngOnDestroy(): void {
    if (this.intervalId) {
      clearInterval(this.intervalId);
    }
  }
}
