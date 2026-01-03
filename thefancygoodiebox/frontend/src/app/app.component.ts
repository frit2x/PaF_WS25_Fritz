import { Component, OnInit, ChangeDetectorRef } from '@angular/core';
import { CommonModule } from '@angular/common';
import { FormsModule } from '@angular/forms';
import { HttpClientModule } from '@angular/common/http';

import { AuthService } from './auth.service';
import { SubscriptionService } from './subscription.service';
import { Subscription } from './models/subscription.model';
import { FormControl, ReactiveFormsModule, FormGroup } from '@angular/forms';
import { CurrencyPipe } from '@angular/common';

@Component({
  selector: 'app-root',
  standalone: true,
  imports: [CommonModule, FormsModule, ReactiveFormsModule, HttpClientModule],
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


  minPrice = 0;
  maxPrice = 100;  // Dynamisch aus Daten ermitteln

  priceForm = new FormGroup({
    minPrice: new FormControl(0),
    maxPrice: new FormControl(100)
  });



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
    this.priceForm.patchValue({ minPrice: 0, maxPrice: 50 });  // ← Engerer Default-Filter
    setTimeout(() => this.loadSubscriptions());
  }

  get loggedIn(): boolean {
    return this.authService.isLoggedIn();
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

  loadSubscriptions(category: string = this.activeCategory): void {
    const minP = this.priceForm.value.minPrice ?? 0;
    const maxP = this.priceForm.value.maxPrice ?? 100;

    this.subscriptionService.getFiltered(category, minP, maxP)
        .subscribe({
          next: subs => {
            this.subscriptions = subs;
            this.infiniteCards = [...subs, ...subs, ...subs];
            this.currentIndex = 0;
            this.cdr.detectChanges();
          },
          error: err => console.error('Load failed:', err)
        });
  }



  // Carousel Methoden

  private resetCarousel(): void {
    // Vereinfachen - nicht mehr nötig, da loadSubscriptions() alles macht
    this.currentIndex = 0;
  }


  scrollLeft(): void {
    this.currentIndex = Math.max(0, this.currentIndex - 1);
  }

  scrollRight(): void {
    const maxIndex = this.infiniteCards.length - 4;
    this.currentIndex = Math.min(maxIndex, this.currentIndex + 1);
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

  setCategory(category: string): void {
    this.activeCategory = category;
    // Force Angular to detect the change
    this.cdr.detectChanges();
    this.loadSubscriptions();
  }


  onPriceChange(): void {
    this.loadSubscriptions();
  }

  filterTimeout: any;
  debounceFilter(): void {
    clearTimeout(this.filterTimeout);
    this.filterTimeout = setTimeout(() => {
      this.loadSubscriptions();
    }, 300);  // 300ms warten
  }

}
