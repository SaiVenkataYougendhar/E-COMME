export declare class ProductPage {
    private container;
    private products;
    private filters;
    constructor(containerId: string);
    loadProducts(): Promise<void>;
    searchProducts(keyword: string): Promise<void>;
    loadByCategory(category: string): Promise<void>;
    applyFilters(): Promise<void>;
    initUIFilters(): void;
    private renderProducts;
}
//# sourceMappingURL=ProductPage.d.ts.map