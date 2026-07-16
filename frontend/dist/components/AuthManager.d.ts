export declare class AuthManager {
    private static instance;
    private isAuthenticated;
    private currentUser;
    private constructor();
    static getInstance(): AuthManager;
    private checkAuth;
    login(email: string, password: string): Promise<boolean>;
    signInWithGoogle(): Promise<boolean>;
    checkoutAsGuest(): Promise<boolean>;
    register(email: string, password: string, firstName: string, lastName: string, phone: string): Promise<boolean>;
    logout(): void;
    isLoggedIn(): boolean;
    getCurrentUser(): any;
    loadUserProfile(): Promise<void>;
}
declare const _default: AuthManager;
export default _default;
//# sourceMappingURL=AuthManager.d.ts.map