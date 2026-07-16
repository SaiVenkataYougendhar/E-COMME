package com.swiftcart.dto;

import java.math.BigDecimal;
import java.util.*;

public class CreateOrderRequest {
    private String paymentMethod;
    private String shippingAddress;
    private Double latitude;
    private Double longitude;

    // Constructors
    public CreateOrderRequest() {}

    public CreateOrderRequest(String paymentMethod, String shippingAddress) {
        this.paymentMethod = paymentMethod;
this.shippingAddress = shippingAddress;
    }

    // Getters and Setters
    public String getPaymentMethod() { return paymentMethod; }
    public void setPaymentMethod(String paymentMethod) { this.paymentMethod = paymentMethod; }
    public String getShippingAddress() { return shippingAddress; }
    public void setShippingAddress(String shippingAddress) { this.shippingAddress = shippingAddress; }
    public Double getLatitude() { return latitude; }
    public void setLatitude(Double latitude) { this.latitude = latitude; }
    public Double getLongitude() { return longitude; }
    public void setLongitude(Double longitude) { this.longitude = longitude; }
}
