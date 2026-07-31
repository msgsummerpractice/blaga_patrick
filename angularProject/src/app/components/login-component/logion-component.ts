import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, NonNullableFormBuilder } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
@Component({
  selector: 'app-logion-component',
  imports: [ReactiveFormsModule],
  templateUrl: './logion-component.html',
  styleUrls: ['./logion-component.css'],
})
export class LogionComponent {
  private readonly _formBuilder: NonNullableFormBuilder = inject(NonNullableFormBuilder);

  loginForm: FormGroup = this._formBuilder.group({
    email: ['', [Validators.required, Validators.email]],
    password: ['', [Validators.required, Validators.minLength(6)]],
  });

  onSubmit(): void {
    if (this.loginForm.valid) {
      console.log('Form Submitted', this.loginForm.getRawValue());
    }
  }
}
