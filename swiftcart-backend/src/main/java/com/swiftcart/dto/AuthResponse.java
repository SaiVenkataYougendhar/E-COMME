package com.swiftcart.dto;

import java.math.BigDecimal;
import java.util.*;

public class AuthResponse {
    private String token;
private String email;
private String firstName;
private String lastName;
private Long userId;

    // Constructors
    public AuthResponse() {}

    public AuthResponse(String token, String email, String firstName, String lastName, Long userId) {
        this.token = token;
this.email = email;
this.firstName = firstName;
this.lastName = lastName;
this.userId = userId;
    }

    // Getters and Setters
    public String getToken() { return token; }
    public void setToken(String token) { this.token = token; }
public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
public Long getUserId() { return userId; }
    public void setUserId(Long userId) { this.userId = userId; }
}
