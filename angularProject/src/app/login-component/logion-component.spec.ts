import { ComponentFixture, TestBed } from '@angular/core/testing';

import { LogionComponent } from './logion-component';

describe('LogionComponent', () => {
  let component: LogionComponent;
  let fixture: ComponentFixture<LogionComponent>;

  beforeEach(async () => {
    await TestBed.configureTestingModule({
      imports: [LogionComponent],
    }).compileComponents();

    fixture = TestBed.createComponent(LogionComponent);
    component = fixture.componentInstance;
    await fixture.whenStable();
  });

  it('should create', () => {
    expect(component).toBeTruthy();
  });
});
