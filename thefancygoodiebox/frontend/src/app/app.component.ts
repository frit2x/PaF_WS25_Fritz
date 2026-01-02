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
  // Basis Properties
  username = '';
  password = '';
  subscriptions: Subscription[] = [];
  cartCount = 0;
  currentUser = '';  // Neue Property

  // Carousel Properties
  activeCategory: string = 'all';
  currentIndex = 0;
  infiniteCards: Subscription[] = [];

  constructor(
      private authService: AuthService,
      private subscriptionService: SubscriptionService,
      private cdr: ChangeDetectorRef
  ) {}

  ngOnInit(): void {
    if (this.loggedIn) {
      this.loadSubscriptions();
    }
  }

  get loggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  // Kategorien Getter (vereinfacht)
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
      this.currentUser = this.username;  // Username merken
      this.loadSubscriptions();
    } else {
      alert('Login fehlgeschlagen (admin / admin)');
    }
  }

  logout(): void {
    this.authService.logout();
    this.currentUser = '';
    this.subscriptions = [];
    this.resetCarousel();
  }

  loadSubscriptions(): void {
    this.subscriptionService.getAll().subscribe(subs => {
      this.subscriptions = subs;
      this.resetCarousel();
      this.cdr.detectChanges();
    });
  }

  // Carousel Methoden
  setCategory(category: string): void {
    this.activeCategory = category;
    this.resetCarousel();
  }

  private resetCarousel(): void {
    let filtered = this.subscriptions;
    if (this.activeCategory !== 'all') {
      filtered = this.subscriptions.filter(s => s.category === this.activeCategory);
    }

    // 3x wiederholen für endlosen Loop
    this.infiniteCards = [...filtered, ...filtered, ...filtered];
    this.currentIndex = 0;
  }

  scrollLeft(): void {
    this.currentIndex = Math.max(0, this.currentIndex - 1);
  }

  scrollRight(): void {
    const maxIndex = this.infiniteCards.length - 4;
    this.currentIndex = Math.min(maxIndex, this.currentIndex + 1);
  }

  get filteredSubs(): Subscription[] {
    return this.activeCategory === 'all'
        ? this.subscriptions
        : this.subscriptions.filter(s => s.category === this.activeCategory);
  }

  discover(abo: Subscription): void {
    alert(`🔥 Entdecke "${abo.name}"!\n\n${abo.description}\n\nSchnapp dir dieses coole Abo für nur ${abo.price}€! 😎`);
  }

  buy(abo: Subscription): void {
    alert(`"${abo.name}" für ${abo.price} € gekauft!`);
    this.cartCount++;
  }

  openCart(): void {
    alert(`Warenkorb (${this.cartCount} Artikel)`);
  }

  trackById(index: number, abo: Subscription): number {
    return abo.id;
  }
}
