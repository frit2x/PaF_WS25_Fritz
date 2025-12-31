export interface SubscriptionDto {
    id: number;
    name: string;
    description: string;
    price: number;
    image_url: string;   // kommt GENAU so vom Backend
    category: string;
}
