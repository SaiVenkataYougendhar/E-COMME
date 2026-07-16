package com.swiftcart.dto;

import java.math.BigDecimal;
import java.util.*;

public class CartItemRequest {
    private Long productId;
private Integer quantity;

    // Constructors
    public CartItemRequest() {}

    public CartItemRequest(Long productId, Integer quantity) {
        this.productId = productId;
this.quantity = quantity;
    }

    // Getters and Setters
    public Long getProductId() { return productId; }
    public void setProductId(Long productId) { this.productId = productId; }
public Integer getQuantity() { return quantity; }
    public void setQuantity(Integer quantity) { this.quantity = quantity; }
}
