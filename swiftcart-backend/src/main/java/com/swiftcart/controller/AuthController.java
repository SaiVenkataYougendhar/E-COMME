package com.swiftcart.controller;

import com.swiftcart.dto.ApiResponse;
import com.swiftcart.dto.LoginRequest;
import com.swiftcart.dto.RegisterRequest;
import com.swiftcart.dto.AuthResponse;
import com.swiftcart.entity.User;
import com.swiftcart.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register")
    public ResponseEntity<ApiResponse<AuthResponse>> register(@RequestBody RegisterRequest request) {
        try {
            AuthResponse response = authService.register(request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Registration successful", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @PostMapping("/login")
    public ResponseEntity<ApiResponse<AuthResponse>> login(@RequestBody LoginRequest request) {
        try {
            AuthResponse response = authService.login(request);
            return ResponseEntity.ok(new ApiResponse<>(true, "Login successful", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, "Invalid credentials"));
        }
    }

    @PostMapping("/google")
    public ResponseEntity<ApiResponse<AuthResponse>> loginWithGoogle(@RequestBody com.swiftcart.dto.GoogleLoginRequest request) {
        try {
            AuthResponse response = authService.loginWithGoogle(request.getIdToken());
            return ResponseEntity.ok(new ApiResponse<>(true, "Google Login successful", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @PostMapping("/guest-token")
    public ResponseEntity<ApiResponse<AuthResponse>> getGuestToken() {
        try {
            AuthResponse response = authService.generateGuestToken();
            return ResponseEntity.ok(new ApiResponse<>(true, "Guest session created", response));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @GetMapping("/profile")
    public ResponseEntity<ApiResponse<User>> getProfile(Authentication authentication) {
        try {
            User user = authService.getUserByEmail(authentication.getName());
            return ResponseEntity.ok(new ApiResponse<>(true, "Profile retrieved", user));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new ApiResponse<>(false, "User not found"));
        }
    }

    @PatchMapping("/profile")
    public ResponseEntity<ApiResponse<User>> updateProfile(
            @RequestBody User userDetails,
            Authentication authentication) {
        try {
            User currentUser = authService.getUserByEmail(authentication.getName());
            User updatedUser = authService.updateUser(currentUser.getId(), userDetails);
            return ResponseEntity.ok(new ApiResponse<>(true, "Profile updated", updatedUser));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }
}


