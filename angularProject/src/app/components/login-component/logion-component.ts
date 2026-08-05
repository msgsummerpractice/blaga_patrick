import { Component, inject } from '@angular/core';
import { FormBuilder, FormGroup, Validators, NonNullableFormBuilder } from '@angular/forms';
import { ReactiveFormsModule } from '@angular/forms';
import { AuthService } from '../../services/auth-service';
import { CommonModule } from '@angular/common';
import { Router } from '@angular/router';
@Component({
  selector: 'app-logion-component',
  imports: [ReactiveFormsModule, CommonModule],
  templateUrl: './logion-component.html',
  styleUrls: ['./logion-component.css'],
})
export class LogionComponent {
  private readonly _formBuilder: NonNullableFormBuilder = inject(NonNullableFormBuilder);
  private readonly authService = inject(AuthService);
  router = inject(Router);
  isOtpStep: boolean = false;
  error: string = '';

  loginForm: FormGroup = this._formBuilder.group({
    username: ['', [Validators.required]],
    password: ['', [Validators.required, Validators.minLength(4)]],
  });

  onSubmit(): void {
    this.error = '';
    console.log('Form submitted:', this.loginForm.value);
    if (this.loginForm.invalid) {
      return;
    }

    const { username, password, otp } = this.loginForm.getRawValue();

    if (!this.isOtpStep) {
      this.authService.login(username, password).subscribe({
        next: (response) => {
          if (response) {
            this.isOtpStep = true;
            this.loginForm.addControl('otp', this._formBuilder.control('', [Validators.required]));
          }
        },
        error: (err) => {
          this.error = err.error.message || 'Login failed';
        },
      });
    } else {
      this.authService.verifyMfa(username, otp).subscribe({
        next: (response) => {
          this.error = '';
          this.router.navigate(['/']);
        },
        error: (err) => {
          this.error = err.error.message || 'OTP verification failed';
        },
      });
    }
  }
}
