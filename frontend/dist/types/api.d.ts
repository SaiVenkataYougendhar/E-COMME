export interface Product {
    id: number;
    name: string;
    description: string;
    price: number;
    discount_price?: number;
    category: string;
    image_url: string;
    stock: number;
    rating: number;
    created_at: string;
    updated_at: string;
}
export interface User {
    id: number;
    email: string;
    first_name: string;
    last_name: string;
    phone: string;
    address: string;
    city: string;
    state: string;
    zip_code: string;
    country: string;
    avatar_url?: string;
    role: 'customer' | 'vendor' | 'admin';
}
export interface CartItem {
    id: number;
    user_id: number;
    product_id: number;
    quantity: number;
    name: string;
    price: number;
    image_url: string;
}
export interface Order {
    id: number;
    user_id: number;
    total_amount: number;
    status: 'pending' | 'processing' | 'shipped' | 'delivered' | 'cancelled';
    payment_method: string;
    shipping_address: string;
    created_at: string;
    items?: OrderItem[];
}
export interface OrderItem {
    id: number;
    order_id: number;
    product_id: number;
    quantity: number;
    price: number;
}
export interface ApiResponse<T> {
    success: boolean;
    data?: T;
    error?: string;
    message?: string;
}
//# sourceMappingURL=api.d.ts.map