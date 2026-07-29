import { Component, Input } from '@angular/core';

@Component({
  selector: 'app-card',
  imports: [],
  templateUrl: './card.html',
  styleUrls: ['./card.css'],
})
export class Card {
  @Input() breed: string = '';
  @Input() description: string = '';
}
