import apiClient from '../services/apiClient';

import { initializeApp } from 'firebase/app';
import { getAuth, signInWithPopup, GoogleAuthProvider, signOut } from 'firebase/auth';

const firebaseConfig = {
  apiKey: process.env.FIREBASE_API_KEY,
  authDomain: process.env.FIREBASE_AUTH_DOMAIN,
  projectId: process.env.FIREBASE_PROJECT_ID,
  storageBucket: process.env.FIREBASE_STORAGE_BUCKET,
  messagingSenderId: process.env.FIREBASE_MESSAGING_SENDER_ID,
  appId: process.env.FIREBASE_APP_ID
};

const app = initializeApp(firebaseConfig);
const auth = getAuth(app);
const googleProvider = new GoogleAuthProvider();

export class AuthManager {
  private static instance: AuthManager;
  private isAuthenticated: boolean = false;
  private currentUser: any = null;

  private constructor() {
    this.checkAuth();
  }

  static getInstance(): AuthManager {
    if (!AuthManager.instance) {
      AuthManager.instance = new AuthManager();
    }
    return AuthManager.instance;
  }

  private checkAuth(): void {
    const token = localStorage.getItem('auth_token');
    this.isAuthenticated = !!token;
  }

  async login(email: string, password: string): Promise<boolean> {
    try {
      const response = await apiClient.login(email, password);
      if (response.success) {
        this.isAuthenticated = true;
        this.currentUser = response.data?.user;
        return true;
      }
      return false;
    } catch (error) {
      console.error('Login error:', error);
      return false;
    }
  }

  async signInWithGoogle(): Promise<boolean> {
    try {
      const result = await signInWithPopup(auth, googleProvider);
      const idToken = await result.user.getIdToken();
      
      const response = await apiClient.loginWithGoogle(idToken);
      if (response.success) {
        this.isAuthenticated = true;
        this.currentUser = response.data?.user;
        return true;
      }
      return false;
    } catch (error) {
      console.error('Google Sign-In error:', error);
      return false;
    }
  }

  async checkoutAsGuest(): Promise<boolean> {
    try {
      const response = await apiClient.getGuestToken();
      if (response.success) {
        this.isAuthenticated = true;
        this.currentUser = response.data?.user;
        return true;
      }
      return false;
    } catch (error) {
      console.error('Guest Sign-In error:', error);
      return false;
    }
  }

  async register(email: string, password: string, firstName: string, lastName: string, phone: string): Promise<boolean> {
    try {
      const response = await apiClient.register(email, password, firstName, lastName, phone);
      if (response.success) {
        return true;
      }
      return false;
    } catch (error) {
      console.error('Registration error:', error);
      return false;
    }
  }

  logout(): void {
    signOut(auth).catch(console.error);
    apiClient.clearAuthToken();
    this.isAuthenticated = false;
    this.currentUser = null;
  }

  isLoggedIn(): boolean {
    return this.isAuthenticated;
  }

  getCurrentUser(): any {
    return this.currentUser;
  }

  async loadUserProfile(): Promise<void> {
    try {
      const response = await apiClient.getProfile();
      if (response.success) {
        this.currentUser = response.data;
      }
    } catch (error) {
      console.error('Error loading profile:', error);
    }
  }
}

export default AuthManager.getInstance();
