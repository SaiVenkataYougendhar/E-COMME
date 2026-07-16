import { ApiResponse, User, Product, CartItem, Order } from '../types/api';
declare class ApiClient {
    private instance;
    private token;
    constructor();
    setAuthToken(token: string): void;
    clearAuthToken(): void;
    register(email: string, password: string, firstName: string, lastName: string, phone: string): Promise<ApiResponse<{
        id: number;
        email: string;
    }>>;
    login(email: string, password: string): Promise<ApiResponse<{
        token: string;
        user: Partial<User>;
    }>>;
    loginWithGoogle(idToken: string): Promise<ApiResponse<{
        token: string;
        user: Partial<User>;
    }>>;
    getGuestToken(): Promise<ApiResponse<{
        token: string;
        user: Partial<User>;
    }>>;
    getProfile(): Promise<ApiResponse<User>>;
    updateProfile(data: Partial<User>): Promise<ApiResponse<void>>;
    getProducts(): Promise<ApiResponse<Product[]>>;
    getProduct(id: number): Promise<ApiResponse<Product>>;
    getProductsByCategory(category: string): Promise<ApiResponse<Product[]>>;
    searchProducts(keyword: string): Promise<ApiResponse<Product[]>>;
    filterProducts(filters: {
        category?: string;
        keyword?: string;
        minPrice?: number;
        maxPrice?: number;
    }): Promise<ApiResponse<Product[]>>;
    getCart(): Promise<ApiResponse<CartItem[]>>;
    addToCart(productId: number, quantity: number): Promise<ApiResponse<void>>;
    updateCartItem(cartId: number, quantity: number): Promise<ApiResponse<void>>;
    removeFromCart(cartId: number): Promise<ApiResponse<void>>;
    clearCart(): Promise<ApiResponse<void>>;
    createOrder(paymentMethod: string, shippingAddress: string, latitude?: number, longitude?: number): Promise<ApiResponse<{
        id: number;
        total_amount: number;
    }>>;
    getOrders(): Promise<ApiResponse<Order[]>>;
    getOrder(id: number): Promise<ApiResponse<Order>>;
    updateOrderStatus(id: number, status: string): Promise<ApiResponse<void>>;
}
declare const _default: ApiClient;
export default _default;
//# sourceMappingURL=apiClient.d.ts.map