package com.swiftcart.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;


@Entity
@Table(name = "users")
public class User {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
private String email;
private String password;
private String firstName;
private String lastName;
private String phone;
private String address;
private String city;
private String state;
private String zipCode;
private String country;
private String avatarUrl;
    @Enumerated(EnumType.STRING)
    private UserRole role;

    // Constructors
    public User() {}

    public User(Long id, String email, String password, String firstName, String lastName, String phone, String address, String city, String state, String zipCode, String country, String avatarUrl, UserRole role) {
        this.id = id;
this.email = email;
this.password = password;
this.firstName = firstName;
this.lastName = lastName;
this.phone = phone;
this.address = address;
this.city = city;
this.state = state;
this.zipCode = zipCode;
this.country = country;
this.avatarUrl = avatarUrl;
this.role = role;
    }

    // Getters and Setters
        public Long getId() { return id; }
    public void setId(Long id) { this.id = id; }
    public String getEmail() { return email; }
    public void setEmail(String email) { this.email = email; }
    public String getPassword() { return password; }
    public void setPassword(String password) { this.password = password; }
    public String getFirstName() { return firstName; }
    public void setFirstName(String firstName) { this.firstName = firstName; }
    public String getLastName() { return lastName; }
    public void setLastName(String lastName) { this.lastName = lastName; }
    public String getPhone() { return phone; }
    public void setPhone(String phone) { this.phone = phone; }
    public String getAddress() { return address; }
    public void setAddress(String address) { this.address = address; }
    public String getCity() { return city; }
    public void setCity(String city) { this.city = city; }
    public String getState() { return state; }
    public void setState(String state) { this.state = state; }
    public String getZipCode() { return zipCode; }
    public void setZipCode(String zipCode) { this.zipCode = zipCode; }
    public String getCountry() { return country; }
    public void setCountry(String country) { this.country = country; }
    public String getAvatarUrl() { return avatarUrl; }
    public void setAvatarUrl(String avatarUrl) { this.avatarUrl = avatarUrl; }
    public UserRole getRole() { return role; }
    public void setRole(UserRole role) { this.role = role; }
}
