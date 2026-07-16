package com.swiftcart.dto;

import java.math.BigDecimal;
import java.util.*;

public class CartItemDTO {
    private Long id;
private Long productId;
private String productName;
private BigDecimal price;
private Integer quantity;
private String imageUrl;
private BigDecimal total;

    // Constructors
    public CartItemDTO() {}

    public CartItemDTO(Long id, Long productId, String productName, BigDecimal price, Integer quantity, String imageUrl, BigDecimal total) {
        this.id = id;
this.productId = productId;
this.productName = productName;
this.price = price;
this.quantity = quantity;
this.imageUrl = imageUrl;
this.total = total;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
public String getProductName() { return productName; }
    public void setProductName(String productName) { this.productName = productName; }
public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
public BigDecimal getTotal() { return total; }
    public void setTotal(BigDecimal total) { this.total = total; }
}
