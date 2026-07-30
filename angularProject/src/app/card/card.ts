import { Component, Input, signal, inject } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { MatButton } from '@angular/material/button';
import { OnInit } from '@angular/core';

@Component({
  selector: 'app-card',
  imports: [MatButton],
  templateUrl: './card.html',
  styleUrls: ['./card.css'],
})
export class Card implements OnInit {
  @Input() breed: string = '';
  @Input() description: string = '';

  dogImage = signal<string>('');
  private http = inject(HttpClient);

  fetchDogImage() {
    this.http
      .get<{ message: string; status: string }>('https://dog.ceo/api/breeds/image/random')
      .subscribe((response) => {
        this.dogImage.set(response.message);
      });
  }

  ngOnInit() {
    this.fetchDogImage();
  }
}
