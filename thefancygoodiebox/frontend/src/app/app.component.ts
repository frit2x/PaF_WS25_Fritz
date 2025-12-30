import { Component, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AuthService } from './auth.service';
import { SubscriptionService } from './subscription.service';
import { Subscription } from './models/subscription.model';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent {

  username = '';
  password = '';
  loggedIn = false;

  subscriptions: Subscription[] = [];

  constructor(
      private authService: AuthService,
      private subscriptionService: SubscriptionService,
      private cdr: ChangeDetectorRef
  ) {}

  login(): void {
    if (this.authService.login(this.username, this.password)) {
      this.loggedIn = true;
      this.loadSubscriptions();
    } else {
      alert('Login fehlgeschlagen (admin / admin)');
    }
  }

  logout(): void {
    this.authService.logout();
    this.loggedIn = false;
    this.subscriptions = [];
  }

  loadSubscriptions(): void {
    this.subscriptionService.getAll().subscribe({
      next: data => {
        this.subscriptions = data;
        this.cdr.detectChanges();
      },
      error: err => {
        console.error('Fehler beim Laden', err);
      }
    });
  }

  buy(abo: Subscription): void {
    alert(`"${abo.name}" für ${abo.price} € gekauft`);
  }
}
