package com.swiftcart.config;

import com.swiftcart.entity.Product;
import com.swiftcart.repository.ProductRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;
import java.math.BigDecimal;

@Component
public class DataSeeder implements CommandLineRunner {

    private final ProductRepository productRepository;

    public DataSeeder(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    @Override
    public void run(String... args) throws Exception {
        if (productRepository.count() == 0) {
            Product p1 = new Product();
            p1.setName("Wireless Noise-Cancelling Headphones");
            p1.setDescription("Premium wireless headphones with active noise cancellation and 30-hour battery life.");
            p1.setPrice(new BigDecimal("299.99"));
            p1.setStock(50);
            p1.setCategory("Electronics");
            p1.setImageUrl("https://images.unsplash.com/photo-1505740420928-5e560c06d30e?w=500&q=80");

            Product p2 = new Product();
            p2.setName("Smart Fitness Watch");
            p2.setDescription("Advanced smartwatch with heart rate monitoring, GPS, and sleep tracking.");
            p2.setPrice(new BigDecimal("199.50"));
            p2.setStock(120);
            p2.setCategory("Wearables");
            p2.setImageUrl("https://images.unsplash.com/photo-1523275335684-37898b6baf30?w=500&q=80");

            Product p3 = new Product();
            p3.setName("Ultra HD 4K Monitor");
            p3.setDescription("27-inch 4K UHD monitor with HDR support and ultra-thin bezels for professionals.");
            p3.setPrice(new BigDecimal("349.00"));
            p3.setStock(30);
            p3.setCategory("Computers");
            p3.setImageUrl("https://images.unsplash.com/photo-1527443224154-c4a3942d3acf?w=500&q=80");

            Product p4 = new Product();
            p4.setName("Mechanical Gaming Keyboard");
            p4.setDescription("RGB backlit mechanical keyboard with tactile switches and programmable macros.");
            p4.setPrice(new BigDecimal("129.99"));
            p4.setStock(85);
            p4.setCategory("Accessories");
            p4.setImageUrl("https://images.unsplash.com/photo-1595225476474-87563907a212?w=500&q=80");

            Product p5 = new Product();
            p5.setName("Ergonomic Office Chair");
            p5.setDescription("Comfortable office chair with lumbar support and adjustable armrests.");
            p5.setPrice(new BigDecimal("210.00"));
            p5.setStock(40);
            p5.setCategory("Furniture");
            p5.setImageUrl("https://images.unsplash.com/photo-1505843490538-5133c6c7d0e1?w=500&q=80");

            Product p6 = new Product();
            p6.setName("Smartphone 5G");
            p6.setDescription("Latest 5G smartphone with 128GB storage and stunning 6.5-inch OLED display.");
            p6.setPrice(new BigDecimal("799.00"));
            p6.setStock(150);
            p6.setCategory("Electronics");
            p6.setImageUrl("https://images.unsplash.com/photo-1511707171634-5f897ff02aa9?w=500&q=80");

            Product p7 = new Product();
            p7.setName("Wireless Mouse");
            p7.setDescription("Ergonomic wireless mouse with 6 programmable buttons and long battery life.");
            p7.setPrice(new BigDecimal("49.99"));
            p7.setStock(200);
            p7.setCategory("Accessories");
            p7.setImageUrl("https://images.unsplash.com/photo-1527864550417-7fd91fc51a46?w=500&q=80");

            Product p8 = new Product();
            p8.setName("Bluetooth Speaker");
            p8.setDescription("Portable Bluetooth speaker with rich bass, waterproof design, and 12-hour playtime.");
            p8.setPrice(new BigDecimal("89.99"));
            p8.setStock(75);
            p8.setCategory("Electronics");
            p8.setImageUrl("https://images.unsplash.com/photo-1608043152269-423dbba4e7e1?w=500&q=80");

            productRepository.save(p1);
            productRepository.save(p2);
            productRepository.save(p3);
            productRepository.save(p4);
            productRepository.save(p5);
            productRepository.save(p6);
            productRepository.save(p7);
            productRepository.save(p8);
        }
    }
}
