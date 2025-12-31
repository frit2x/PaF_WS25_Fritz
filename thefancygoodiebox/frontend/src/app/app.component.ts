import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
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
export class AppComponent implements OnInit {

  username = '';
  password = '';
  subscriptions: Subscription[] = [];

  constructor(
      private authService: AuthService,
      private subscriptionService: SubscriptionService,
      private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.loggedIn) {
      this.loadSubscriptions(); // automatisch beim Start, falls eingeloggt
    }
  }

  get loggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  // Kategorien Getter
  get officeSubs(): Subscription[] {
    return this.subscriptions.filter(s => s.category === 'office');
  }
  get nerdSubs(): Subscription[] {
    return this.subscriptions.filter(s => s.category === 'nerd');
  }
  get foodSubs(): Subscription[] {
    return this.subscriptions.filter(s => s.category === 'food');
  }

  login(): void {
    if (this.authService.login(this.username, this.password)) {
      this.loadSubscriptions();
    } else {
      alert('Login fehlgeschlagen (admin / admin)');
    }
  }

  logout(): void {
    this.authService.logout();
    this.subscriptions = [];
  }

  loadSubscriptions(): void {
    this.subscriptionService.getAll()
        .subscribe(subs => {
          this.subscriptions = subs;
          this.cdr.detectChanges(); // zwingt Angular, die Ansicht zu aktualisieren
        });
  }

  buy(abo: Subscription): void {
    alert(`"${abo.name}" für ${abo.price} € gekauft`);
  }
}
