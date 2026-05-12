import { Component } from '@angular/core';
import { Router } from '@angular/router';
import { FormsModule } from '@angular/forms';
import { CommonModule } from '@angular/common';
import { AuthService } from '../../core/services/auth';

@Component({
  selector: 'app-login',
  standalone: true,
  imports: [FormsModule, CommonModule],
  templateUrl: './login.html',
  styleUrls: ['./login.scss']
})
export class Login {

  email = '';
  password = '';
  error = '';

  constructor(
    private authService: AuthService,
    private router: Router
  ) {}

  login() {
    this.authService.login(this.email, this.password).subscribe({
      next: (res: any) => {
        localStorage.setItem('token', res.token);

        const rol = res.rol || res.usuario?.rol?.nombreRol;

        if (rol === 'CLIENTE') {
          this.router.navigate(['/cliente']);
        } else if (rol === 'TAROTISTA') {
          this.router.navigate(['/tarotista']);
        } else {
          this.router.navigate(['/admin']);
        }
      },
      error: () => {
        this.error = 'Credenciales incorrectas';
      }
    });
  }
}