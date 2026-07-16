import { Product } from '../types/api';
import apiClient from '../services/apiClient';
import { ProductCard } from '../components/ProductCard';

export class ProductPage {
  private container: HTMLElement;
  private products: Product[] = [];
  private filters: { category?: string; keyword?: string; minPrice?: number; maxPrice?: number } = {};

  constructor(containerId: string) {
    const elem = document.getElementById(containerId);
    if (!elem) throw new Error(`Container with id ${containerId} not found`);
    this.container = elem;
  }

  async loadProducts(): Promise<void> {
    await this.applyFilters();
  }

  async searchProducts(keyword: string): Promise<void> {
    this.filters.keyword = keyword;
    await this.applyFilters();
  }

  async loadByCategory(category: string): Promise<void> {
    this.filters.category = category;
    await this.applyFilters();
  }

  async applyFilters(): Promise<void> {
    try {
      const response = await apiClient.filterProducts(this.filters);
      if (response.success && response.data) {
        this.products = response.data;
        this.renderProducts();
      }
    } catch (error) {
      console.error('Error applying filters:', error);
      this.container.innerHTML = '<p class="error">Error loading products</p>';
    }
  }

  public initUIFilters(): void {
    // Category checkboxes
    const categoryInputs = document.querySelectorAll('#cagetory_widget_collapse input[type="checkbox"]');
    categoryInputs.forEach(input => {
      input.addEventListener('change', (e) => {
        const target = e.target as HTMLInputElement;
        if (target.checked) {
          // Uncheck others
          categoryInputs.forEach(other => { if (other !== target) (other as HTMLInputElement).checked = false; });
          const categoryName = target.nextElementSibling?.textContent?.trim() || '';
          this.filters.category = categoryName === 'All' ? undefined : categoryName;
          this.applyFilters();
        }
      });
    });

    // Price range filter
    const filterBtn = document.querySelector('.ranger-min-max-block input[type="submit"]');
    if (filterBtn) {
      filterBtn.addEventListener('click', (e) => {
        e.preventDefault();
        const minInput = document.querySelector('.ranger-min-max-block .min') as HTMLInputElement;
        const maxInput = document.querySelector('.ranger-min-max-block .max') as HTMLInputElement;
        const minVal = minInput && minInput.value ? parseFloat(minInput.value.replace('$', '')) : undefined;
        const maxVal = maxInput && maxInput.value ? parseFloat(maxInput.value.replace('$', '')) : undefined;
        this.filters.minPrice = isNaN(minVal!) ? undefined : minVal;
        this.filters.maxPrice = isNaN(maxVal!) ? undefined : maxVal;
        this.applyFilters();
      });
    }
  }

  private renderProducts(): void {
    this.container.innerHTML = '';

    if (this.products.length === 0) {
      this.container.innerHTML = '<div class="col-12"><p class="no-products">No products found for the selected filters.</p></div>';
      return;
    }

    this.products.forEach(product => {
      const cardContainer = document.createElement('div');
      cardContainer.className = 'col-md-4 col-sm-6';
      const card = new ProductCard(product, cardContainer);
      cardContainer.appendChild(card.render());
      this.container.appendChild(cardContainer);
    });
  }
}
