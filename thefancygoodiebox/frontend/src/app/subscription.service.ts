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

    /** NEU: Filter-Methode */
    getFiltered(category?: string, minPrice?: number, maxPrice?: number): Observable<Subscription[]> {
        let params = new HttpParams();
        if (category && category !== 'all') params = params.set('category', category);
        if (minPrice !== undefined) params = params.set('minPrice', minPrice.toString());  // ← Auch 0!
        if (maxPrice !== undefined) params = params.set('maxPrice', maxPrice.toString());  // Auch 100!

        console.log('→ Request:', this.baseUrl + '?' + params.toString());
        return this.http.get<SubscriptionDto[]>(this.baseUrl, { params }).pipe(
            map(dtos => {
                console.log('← Raw DTOs:', dtos.length);
                return dtos.map(d => ({
                    id: d.id, name: d.name, description: d.description, price: d.price,
                    imageUrl: (d as any).imageUrl || d['image_url'] || '', category: d.category
                } as Subscription));
            })
        );
    }


}
