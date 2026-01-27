import { HttpClient } from '@angular/common/http';
import { CommonModule } from '@angular/common';
import { CartPreview } from '../models/cart-preview';
import { Component, OnInit, Input } from '@angular/core';

interface CartItem {
    id: number;
    name: string;
    price: number;
}

@Component({
    selector: 'app-cart-preview',
    standalone: true,
    imports: [CommonModule],
    template: `
    <!-- Klickbarer Preview -->
    <!-- Nur Warenkorb-Symbol (klickbar) -->
    <div class="cart-preview" (click)="toggleCart()">
        <div class="cart-icon">
            🛒
            <span class="count-badge" *ngIf="cartItems.length > 0">{{ cartItems.length }}</span>
        </div>
    </div>
    
    <!-- Modal Fenster -->
    <div class="modal-overlay" *ngIf="showCart" (click)="closeCart()">
      <div class="modal-content" (click)="$event.stopPropagation()">
        <div class="modal-header">
          <h3>Warenkorb ({{ cartItems.length }})</h3>
          <button class="close-btn" (click)="closeCart()">✕</button>
        </div>
        
        <!-- Items Liste -->
        <div class="cart-items">
          <div *ngFor="let item of cartItems" class="cart-item">
            <strong>{{ item.name }}</strong><br>
            {{ item.price | currency:'EUR' }}
          </div>
        </div>
        
        <!-- Preise -->
        <div class="cart-summary">
          <div>Zwischensumme: {{ cartPreview.subtotal | currency:'EUR' }}</div>
          <div>Rabatt ({{ cartPreview.strategy }}): -{{ cartPreview.discount | currency:'EUR' }}</div>
          <hr>
          <div class="total">Gesamt: {{ cartPreview.total | currency:'EUR' }}</div>
        </div>
        
        <div class="modal-actions">
          <button class="btn-checkout" (click)="checkout()">Zur Kasse</button>
          <button class="btn-close" (click)="closeCart()">Schließen</button>
          <button class="btn-clear" (click)="clearCart()">🗑️ Leeren</button>

        </div>
      </div>
    </div>
  `,
    styles: [`
    .cart-preview {
        display: inline-flex; gap: 0; align-items: center;
        color: white; border-radius: 25px; cursor: pointer; font-size: 24px;
        font-weight: bold; transition: transform 0.2s;
    }
    .cart-preview:hover { transform: scale(1.05); }
    
    .modal-overlay {
      position: fixed; top: 0; left: 0; right: 0; bottom: 0;
      background: rgba(0,0,0,0.7); z-index: 1000; display: flex; align-items: center; justify-content: center;
    }
    .modal-content {
      background: white; border-radius: 15px; width: 90%; max-width: 500px; max-height: 80vh; overflow-y: auto;
      box-shadow: 0 20px 40px rgba(0,0,0,0.3);
    }
    .modal-header {
      display: flex; justify-content: space-between; align-items: center;
      padding: 20px 25px 10px; border-bottom: 1px solid #eee;
    }
    .close-btn { background: none; border: none; font-size: 24px; cursor: pointer; }
    
    .cart-items {
      padding: 20px 25px; max-height: 300px; overflow-y: auto;
    }
    .cart-item {
      padding: 12px 0; border-bottom: 1px solid #f0f0f0;
    }
    
    .cart-summary {
      padding: 20px 25px; background: #f8f9ff;
    }
    .cart-summary div { margin: 8px 0; }
    .total {
      font-size: 24px; font-weight: bold; color: #28a745; text-align: right;
    }
    
    .modal-actions {
      padding: 20px 25px; display: flex; gap: 10px; justify-content: flex-end;
    }
    .btn-checkout {
      background: #28a745; color: white; border: none; padding: 12px 24px;
      border-radius: 8px; font-weight: bold; cursor: pointer;
    }
    .btn-clear {
        background: #dc3545; color: white; border: none;
        padding: 12px 16px; border-radius: 8px; cursor: pointer;
    }
    .btn-close { background: #6c757d; color: white; border: none; padding: 12px 24px; border-radius: 8px; }
  `]
})
export class CartPreviewComponent implements OnInit {
    cartPreview: CartPreview = { subtotal: 0, discount: 0, total: 0, strategy: '' };
    cartItems: CartItem[] = [];
    @Input() username = 'student';
    showCart = false;

    constructor(private http: HttpClient) {}

    ngOnInit(): void {
        this.updatePreview();
        setInterval(() => this.updatePreview(), 2000);
    }

    updatePreview(): void {
        this.http.get<CartPreview>('http://localhost:8081/cart/preview', {
            params: { username: this.username }  // ← Fix 'student' OK, oder dynamisch machen
        }).subscribe(preview => this.cartPreview = { ...preview });
    }

    toggleCart(): void {
        this.showCart = true;
        this.loadCartItems();
    }

    // loadCartItems() dynamisch machen (Backend-sync):
    loadCartItems(): void {
        // Backend-Call für echte Items (füge Endpoint hinzu oder Mock mit subtotal)
        this.http.get<any[]>('http://localhost:8081/cart/items', {
            params: { username: this.username }
        }).subscribe(items => {
            this.cartItems = items.map(i => ({
                id: i.id,
                name: i.name,
                price: i.price
            }));
        });
    }


    checkout(): void {
        console.log('💳 Checkout:', this.cartPreview.total);
        this.closeCart();
    }

    closeCart(): void {
        this.showCart = false;
    }

    // ClearCart mit Backend:
    clearCart(): void {
        this.http.delete<void>('http://localhost:8081/cart/clear', {
            params: { username: this.username }
        }).subscribe({
            next: () => {
                this.cartItems = [];  // Sofort leer
                this.updatePreview(); // Rabatt/Preise neu
                console.log('🗑️ Backend geleert');
            },
            error: () => { this.cartItems = []; } // Fallback
        });
    }
}
