import { Component, Input, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatButton } from '@angular/material/button';

@Component({
  selector: 'app-card',
  imports: [MatButton],
  templateUrl: './card.html',
  styleUrls: ['./card.css'],
})
export class Card {
  @Input() breed: string = '';
  @Input() description: string = '';

  dogImage = signal<string>('');
  private http = inject(HttpClient);

  fetchDogImage() {
    this.http
      .get<{ message: string; status: string }>('https://dog.ceo/api/breeds/images/random')
      .subscribe((response) => {
        this.dogImage.set(response.message);
      });
  }
}
