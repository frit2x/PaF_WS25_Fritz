import { Injectable } from '@angular/core';
import { HttpClient } from '@angular/common/http';
import { Observable } from 'rxjs';
import { map } from 'rxjs/operators';
import { Subscription } from './models/subscription.model';
import { SubscriptionDto } from './models/subscription.dto';



@Injectable({
    providedIn: 'root'
})
export class SubscriptionService {

    private baseUrl = 'http://localhost:8081/subscriptions';

    constructor(private http: HttpClient) {}

    /**
     * Holt alle Subscriptions vom Backend und mapped image_url → imageUrl
     */
    getAll(): Observable<Subscription[]> {
        return this.http.get<SubscriptionDto[]>(this.baseUrl)
            .pipe(
                map(dtos => dtos.map(d => ({
                    id: d.id,
                    name: d.name,
                    description: d.description,
                    price: d.price,
                    imageUrl: (d as any).imageUrl || d['image_url'] || '' , // snake → camel
                    category: d.category
                })))
            );
    }
}
