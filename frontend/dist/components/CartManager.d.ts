export declare class CartManager {
    private items;
    private container;
    constructor(container: HTMLElement);
    loadCart(): Promise<void>;
    private render;
    private renderSummary;
    private updateQuantity;
    private removeItem;
    getCartTotal(): number;
}
//# sourceMappingURL=CartManager.d.ts.map