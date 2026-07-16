package com.swiftcart.dto;

import java.math.BigDecimal;
import java.util.*;

public class ProductDTO {
    private Long id;
private String name;
private String description;
private BigDecimal price;
private BigDecimal discountPrice;
private String category;
private String imageUrl;
private Integer stock;
private Float rating;

    // Constructors
    public ProductDTO() {}

    public ProductDTO(Long id, String name, String description, BigDecimal price, BigDecimal discountPrice, String category, String imageUrl, Integer stock, Float rating) {
        this.id = id;
this.name = name;
this.description = description;
this.price = price;
this.discountPrice = discountPrice;
this.category = category;
this.imageUrl = imageUrl;
this.stock = stock;
this.rating = rating;
    }

    // Getters and Setters
    public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
public String getName() { return name; }
    public void setName(String name) { this.name = name; }
public String getDescription() { return description; }
    public void setDescription(String description) { this.description = description; }
public BigDecimal getPrice() { return price; }
    public void setPrice(BigDecimal price) { this.price = price; }
public BigDecimal getDiscountPrice() { return discountPrice; }
    public void setDiscountPrice(BigDecimal discountPrice) { this.discountPrice = discountPrice; }
public String getCategory() { return category; }
    public void setCategory(String category) { this.category = category; }
public String getImageUrl() { return imageUrl; }
    public void setImageUrl(String imageUrl) { this.imageUrl = imageUrl; }
public Integer getStock() { return stock; }
    public void setStock(Integer stock) { this.stock = stock; }
public Float getRating() { return rating; }
    public void setRating(Float rating) { this.rating = rating; }
}
