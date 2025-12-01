import { Component, OnInit } from '@angular/core';
import { FormBuilder, FormGroup, Validators } from '@angular/forms';
import { Router } from '@angular/router';
import { AuthService } from '../../services/auth.service';

@Component({
  selector: 'app-login',
  templateUrl: './login.component.html',
  styleUrls: ['./login.component.css']
})
export class LoginComponent implements OnInit {

  loginForm!: FormGroup;

  constructor(
    private fb: FormBuilder,
    private authService: AuthService,
    private router: Router
  ) {}

  ngOnInit(): void {
    this.loginForm = this.fb.group({
      email: ['', [Validators.required, Validators.email]],
      senha: ['', [Validators.required]]
    });
  }

  login(): void {
    if (this.loginForm.invalid) return;

    const creds = {
      email: this.loginForm.value.email,
      senha: this.loginForm.value.senha
    };

    this.authService.authenticate(creds).subscribe(
      (response: any) => {
        const token = response.replace('Bearer ', '');
        this.authService.sucessfulLogin(token);
        this.router.navigate(['']);
      },
      error => console.error(error)
    );
  }
}
