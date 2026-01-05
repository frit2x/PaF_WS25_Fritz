import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AuthService } from './auth.service';           // Login/Logout-Logik
import { SubscriptionService } from './subscription.service'; // Backend-API Calls
import { Subscription } from './models/subscription.model';   // TypeScript Interface
import { FormControl, ReactiveFormsModule, FormGroup } from '@angular/forms';

@Component({
  selector: 'app-root',
  standalone: true,  // Angular 17+ Standalone Components (kein NgModule nötig!)
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule],
  templateUrl: './app.component.html',
  styleUrls: ['./app.component.css']
})
export class AppComponent implements OnInit {

  // === AUTHENTIFIZIERUNG ===
  username = '';
  password = '';
  currentUser = '';  // Aktueller Benutzername (nach Login)

  // === DATEN & FILTER ===
  subscriptions: Subscription[] = [];  // Hauptliste (gefiltert vom Backend)
  cartCount = 0;                       // Warenkorb-Zähler
  minPrice = 0;
  maxPrice = 100;

  // Reactive Form für Preisfilter (Two-Way-Binding im Template)
  priceForm = new FormGroup({
    minPrice: new FormControl(0),
    maxPrice: new FormControl(100)
  });

  // === CAROUSEL / INFINITE SCROLL ===
  activeCategory: string = 'all';      // Aktive Kategorie-Filter
  currentIndex = 0;                    // Scroll-Position
  infiniteCards: Subscription[] = [];  // 3x duplizierte Liste für Endless-Carousel

  constructor(
      private authService: AuthService,        // Simple Auth (admin/admin)
      private subscriptionService: SubscriptionService,  // HTTP zu Backend
      private cdr: ChangeDetectorRef           // Force Change Detection
  ) {}

  ngOnInit(): void {
    // Default-Filter setzen + Subscriptions laden
    this.priceForm.patchValue({ minPrice: 0, maxPrice: 50 });
    setTimeout(() => this.loadSubscriptions());  // Backend-Call nach DOM-Ready
  }

  // Computed Property: Prüft Login-Status
  get loggedIn(): boolean {
    return this.authService.isLoggedIn();
  }

  /** LOGIN: Benutzer + PW → Service → currentUser speichern */
  login(): void {
    if (this.authService.login(this.username, this.password)) {
      this.currentUser = this.username;
      this.loadSubscriptions();  // Nach Login: Daten neu laden
    } else {
      alert('Login fehlgeschlagen (admin / admin)');
    }
  }

  /** LOGOUT: Session clear + UI reset */
  logout(): void {
    this.authService.logout();
    this.currentUser = '';
    this.subscriptions = [];
    this.resetCarousel();
  }

  /**
   * ZENTRAL: Lädt gefilterte Subscriptions vom Backend
   * GET /subscriptions?category=...&minPrice=...&maxPrice=...
   */
  loadSubscriptions(category: string = this.activeCategory): void {
    const minP = this.priceForm.value.minPrice ?? 0;
    const maxP = this.priceForm.value.maxPrice ?? 100;

    // HTTP GET → Backend → Observable → Subscriptions updaten
    this.subscriptionService.getFiltered(category, minP, maxP)
        .subscribe({
          next: subs => {
            this.subscriptions = subs;                           // Hauptliste
            this.infiniteCards = [...subs, ...subs, ...subs];    // 3x für Infinite Scroll
            this.currentIndex = 0;                               // Reset Scroll
            this.cdr.detectChanges();                            // Force UI-Update
          },
          error: err => console.error('Load failed:', err)
        });
  }

  // === CAROUSEL CONTROLS ===
  resetCarousel(): void {
    this.currentIndex = 0;  // Scroll-Position resetten
  }

  scrollLeft(): void {
    this.currentIndex = Math.max(0, this.currentIndex - 1);
  }

  scrollRight(): void {
    const maxIndex = this.infiniteCards.length - 4;  // 4 sichtbare Cards
    this.currentIndex = Math.min(maxIndex, this.currentIndex + 1);
  }

  // === USER ACTIONS ===
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

  // *ngFor Performance-Optimierung
  trackById(index: number, abo: Subscription): number {
    return abo.id;
  }

  /** KATEGORIE-FILTER: UI + Backend neu laden */
  setCategory(category: string): void {
    this.activeCategory = category;
    this.cdr.detectChanges();
    this.loadSubscriptions();  // Backend-Call mit neuer Kategorie
  }

  /** PREIS-FILTER: Bei Änderung → debounce → Backend */
  onPriceChange(): void {
    this.loadSubscriptions();
  }

  filterTimeout: any;
  debounceFilter(): void {
    clearTimeout(this.filterTimeout);
    this.filterTimeout = setTimeout(() => {
      this.loadSubscriptions();  // 300ms warten nach letzter Eingabe
    }, 300);
  }
}
