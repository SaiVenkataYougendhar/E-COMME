import axios, { AxiosInstance, AxiosError } from 'axios';
import { ApiResponse, User, Product, CartItem, Order } from '../types/api';

class ApiClient {
  private instance: AxiosInstance;
  private token: string | null = null;

  constructor() {
    this.instance = axios.create({
      baseURL: process.env.API_URL || 'http://localhost:8080/api',
      timeout: 10000,
      headers: {
        'Content-Type': 'application/json'
      }
    });

    // Load token from localStorage
    this.token = localStorage.getItem('auth_token');
    if (this.token) {
      this.setAuthToken(this.token);
    }

    // Add response interceptor
    this.instance.interceptors.response.use(
      response => response,
      (error: AxiosError) => {
        if (error.response?.status === 401) {
          localStorage.removeItem('auth_token');
          window.location.href = '/swiftcart-html/';
        }
        return Promise.reject(error);
      }
    );
  }

  setAuthToken(token: string): void {
    this.token = token;
    localStorage.setItem('auth_token', token);
    this.instance.defaults.headers.common['Authorization'] = `Bearer ${token}`;
  }

  clearAuthToken(): void {
    this.token = null;
    localStorage.removeItem('auth_token');
    delete this.instance.defaults.headers.common['Authorization'];
  }

  // Auth endpoints
  async register(email: string, password: string, firstName: string, lastName: string, phone: string): Promise<ApiResponse<{ id: number; email: string }>> {
    const response = await this.instance.post('/auth/register', {
      email,
      password,
      firstName,
      lastName,
      phone
    });
    return response.data;
  }

  async login(email: string, password: string): Promise<ApiResponse<{ token: string; user: Partial<User> }>> {
    const response = await this.instance.post('/auth/login', { email, password });
    if (response.data.success && response.data.data?.token) {
      this.setAuthToken(response.data.data.token);
    }
    return response.data;
  }

  async loginWithGoogle(idToken: string): Promise<ApiResponse<{ token: string; user: Partial<User> }>> {
    const response = await this.instance.post('/auth/google', { idToken });
    if (response.data.success && response.data.data?.token) {
      this.setAuthToken(response.data.data.token);
    }
    return response.data;
  }

  async getGuestToken(): Promise<ApiResponse<{ token: string; user: Partial<User> }>> {
    const response = await this.instance.post('/auth/guest-token');
    if (response.data.success && response.data.data?.token) {
      this.setAuthToken(response.data.data.token);
    }
    return response.data;
  }

  async getProfile(): Promise<ApiResponse<User>> {
    const response = await this.instance.get('/auth/profile');
    return response.data;
  }

  async updateProfile(data: Partial<User>): Promise<ApiResponse<void>> {
    const response = await this.instance.patch('/auth/profile', data);
    return response.data;
  }

  // Product endpoints
  async getProducts(): Promise<ApiResponse<Product[]>> {
    const response = await this.instance.get('/products');
    return response.data;
  }

  async getProduct(id: number): Promise<ApiResponse<Product>> {
    const response = await this.instance.get(`/products/${id}`);
    return response.data;
  }

  async getProductsByCategory(category: string): Promise<ApiResponse<Product[]>> {
    const response = await this.instance.get(`/products/category/${category}`);
    return response.data;
  }

  async searchProducts(keyword: string): Promise<ApiResponse<Product[]>> {
    const response = await this.instance.get('/products/search', { params: { keyword } });
    return response.data;
  }

  async filterProducts(filters: { category?: string; keyword?: string; minPrice?: number; maxPrice?: number }): Promise<ApiResponse<Product[]>> {
    const response = await this.instance.get('/products/filter', { params: filters });
    return response.data;
  }

  // Cart endpoints
  async getCart(): Promise<ApiResponse<CartItem[]>> {
    const response = await this.instance.get('/cart');
    return response.data;
  }

  async addToCart(productId: number, quantity: number): Promise<ApiResponse<void>> {
    const response = await this.instance.post('/cart/add', { productId, quantity });
    return response.data;
  }

  async updateCartItem(cartId: number, quantity: number): Promise<ApiResponse<void>> {
    const response = await this.instance.patch(`/cart/${cartId}`, { quantity });
    return response.data;
  }

  async removeFromCart(cartId: number): Promise<ApiResponse<void>> {
    const response = await this.instance.delete(`/cart/${cartId}`);
    return response.data;
  }

  async clearCart(): Promise<ApiResponse<void>> {
    const response = await this.instance.delete('/cart');
    return response.data;
  }

  // Order endpoints
  async createOrder(paymentMethod: string, shippingAddress: string, latitude?: number, longitude?: number): Promise<ApiResponse<{ id: number; total_amount: number }>> {
    const response = await this.instance.post('/orders', { paymentMethod, shippingAddress, latitude, longitude });
    return response.data;
  }

  async getOrders(): Promise<ApiResponse<Order[]>> {
    const response = await this.instance.get('/orders');
    return response.data;
  }

  async getOrder(id: number): Promise<ApiResponse<Order>> {
    const response = await this.instance.get(`/orders/${id}`);
    return response.data;
  }

  async updateOrderStatus(id: number, status: string): Promise<ApiResponse<void>> {
    const response = await this.instance.patch(`/orders/${id}/status`, { status });
    return response.data;
  }
}

export default new ApiClient();
