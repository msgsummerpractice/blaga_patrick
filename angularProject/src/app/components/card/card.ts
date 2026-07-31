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
      .get<{ id: string; url: string; width: number; height: number }[]>(
        'https://api.thecatapi.com/v1/images/search',
      )
      .subscribe((response) => {
        this.dogImage.set(response[0].url);
      });
  }

  ngOnInit() {
    this.fetchDogImage();
  }
}
