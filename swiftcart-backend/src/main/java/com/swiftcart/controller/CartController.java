package com.swiftcart.controller;

import com.swiftcart.dto.ApiResponse;
import com.swiftcart.dto.CartItemDTO;
import com.swiftcart.dto.CartItemRequest;
import com.swiftcart.repository.UserRepository;
import com.swiftcart.service.CartService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/cart")
public class CartController {

    private final CartService cartService;
    private final UserRepository userRepository;

    public CartController(CartService cartService, UserRepository userRepository) {
        this.cartService = cartService;
        this.userRepository = userRepository;
    }

    @GetMapping
    public ResponseEntity<ApiResponse<List<CartItemDTO>>> getCart(Authentication authentication) {
        try {
            Long userId = getUserIdFromAuthentication(authentication);
            List<CartItemDTO> cartItems = cartService.getCartItems(userId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart retrieved", cartItems));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @PostMapping("/add")
    public ResponseEntity<ApiResponse<CartItemDTO>> addToCart(
            @RequestBody CartItemRequest request,
            Authentication authentication) {
        try {
            Long userId = getUserIdFromAuthentication(authentication);
            CartItemDTO cartItem = cartService.addToCart(userId, request);
            return ResponseEntity.status(HttpStatus.CREATED)
                    .body(new ApiResponse<>(true, "Item added to cart", cartItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @PatchMapping("/{cartId}")
    public ResponseEntity<ApiResponse<CartItemDTO>> updateCartItem(
            @PathVariable Long cartId,
            @RequestBody CartItemRequest request) {
        try {
            CartItemDTO cartItem = cartService.updateCartItem(cartId, request.getQuantity());
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart item updated", cartItem));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @DeleteMapping("/{cartId}")
    public ResponseEntity<ApiResponse<Void>> removeFromCart(@PathVariable Long cartId) {
        try {
            cartService.removeFromCart(cartId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Item removed from cart"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    @DeleteMapping
    public ResponseEntity<ApiResponse<Void>> clearCart(Authentication authentication) {
        try {
            Long userId = getUserIdFromAuthentication(authentication);
            cartService.clearCart(userId);
            return ResponseEntity.ok(new ApiResponse<>(true, "Cart cleared"));
        } catch (Exception e) {
            return ResponseEntity.badRequest()
                    .body(new ApiResponse<>(false, e.getMessage()));
        }
    }

    private Long getUserIdFromAuthentication(Authentication authentication) {
        String email = authentication.getName();
        return userRepository.findByEmail(email)
                .map(user -> user.getId())
                .orElseThrow(() -> new RuntimeException("User not found"));
    }
}


