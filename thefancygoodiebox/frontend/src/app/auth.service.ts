import { Injectable } from '@angular/core';

@Injectable({
  providedIn: 'root'  // Singleton-Service: Einmal erstellt, app-weit verfügbar
})
export class AuthService {

  // === SIMPLE STATE MANAGEMENT ===
  private loggedIn = false;  // Interner Login-State (nicht persistent)

  // === HARDCODED USERS (Development only!) ===
  private readonly users: { [key: string]: string } = {
    'student': 'password123',  // Test-User 1
    'admin': 'admin',          // Admin-Zugang
    'testuser': 'abc'          // Dritter Test-User
  };

  /**
   * LOGIN: Prüft Username/Password gegen hartecodete Users
   * SETZT loggedIn = true bei Erfolg
   * Development-only! Production → Backend-API oder Keycloak
   */
  login(username: string, password: string): boolean {
    // Username existiert UND Password stimmt?
    const validPassword = this.users[username];
    if (validPassword && password === validPassword) {
      this.loggedIn = true;     // State flippen
      console.log(`✅ ${username} logged in`);  // DEBUG
      return true;              // Component: UI updaten
    }
    console.log('❌ Login failed');
    return false;               // Component: alert('Fehlgeschlagen')
  }

  /**
   * LOGOUT: State zurücksetzen
   * Component cleared localStorage + UI separat
   */
  logout(): void {
    this.loggedIn = false;
    console.log('👋 Logged out');
  }

  /**
   * GETTER: Prüft aktuellen Login-Status
   * Template: *ngIf="loggedIn" nutzt das
   */
  isLoggedIn(): boolean {
    return this.loggedIn;
  }
}

