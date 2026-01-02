import { Injectable } from '@angular/core';

@Injectable({ providedIn: 'root' })
export class AuthService {

  private loggedIn = false;
  private readonly users: { [key: string]: string } = {
    'student': 'password123',
    'admin': 'admin',
    'testuser': 'abc'
  };

  login(username: string, password: string): boolean {
    const validPassword = this.users[username];
    if (validPassword && password === validPassword) {
      this.loggedIn = true;
      return true;
    }
    return false;
  }

  logout(): void {
    this.loggedIn = false;
  }

  isLoggedIn(): boolean {
    return this.loggedIn;
  }
}
