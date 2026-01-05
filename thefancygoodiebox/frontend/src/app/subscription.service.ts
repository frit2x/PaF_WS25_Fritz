// src/app/services/subscription.service.ts (oder wo er ist)
import { Injectable } from '@angular/core';
import { HttpClient, HttpParams } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Subscription } from './models/subscription.model';
import { SubscriptionDto } from './models/subscription.dto';


@Injectable({ providedIn: 'root' })
export class SubscriptionService {
    private baseUrl = 'http://localhost:8081/subscriptions';

    constructor(private http: HttpClient) {}

    getAll(): Observable<Subscription[]> {
        return this.http.get<SubscriptionDto[]>(this.baseUrl).pipe(map(dtos => dtos.map(d => ({
            id: d.id, name: d.name, description: d.description, price: d.price,
            imageUrl: (d as any).imageUrl || d['image_url'] || '', category: d.category
        }))));
    }

    /**
     * Filter-Methode für Backend-Subscriptions
     * Baut HTTP-GET-Request mit dynamischen Query-Parametern und mapped DTOs → Subscription-Model
     */
    getFiltered(category?: string, minPrice?: number, maxPrice?: number): Observable<Subscription[]> {
        // 1. Intelligentes HttpParams-Objekt bauen (Query-String für Backend @RequestParam)
        let params = new HttpParams();

        // Kategorie nur senden wenn nicht 'all' (Backend: kein Filter)
        if (category && category !== 'all') {
            params = params.set('category', category);
        }

        // Preise immer senden (auch 0/100 → Backend filtert korrekt)
        if (minPrice !== undefined) {
            params = params.set('minPrice', minPrice.toString());  // Auch 0 wird gesendet!
        }
        if (maxPrice !== undefined) {
            params = params.set('maxPrice', maxPrice.toString());  // Auch 100 wird gesendet!
        }

        // DEBUG: Exakte Request-URL für Troubleshooting
        console.log('→ Request:', this.baseUrl + '?' + params.toString());

        // 2. HTTP GET → Backend Controller → Observable<SubscriptionDto[]>
        return this.http.get<SubscriptionDto[]>(this.baseUrl, { params }).pipe(
            // 3. RxJS map(): Backend-DTOs → Frontend TypeScript Model
            map(dtos => {
                console.log('← Raw DTOs:', dtos.length);  // Response-Größe loggen

                // 4. JEDES DTO manuell zu Subscription mappen
                // snake_case (Backend) → camelCase (Frontend)
                return dtos.map(d => ({
                    id: d.id,
                    name: d.name,
                    description: d.description,
                    price: d.price,
                    // Robuste imageUrl-Handhabung (beide Namensvarianten)
                    imageUrl: (d as any).imageUrl || d['image_url'] || '',
                    category: d.category
                } as Subscription));
            })
        );
    }



}
