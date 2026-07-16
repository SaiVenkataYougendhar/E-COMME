package com.swiftcart.service;

import com.swiftcart.dto.CartItemDTO;
import com.swiftcart.dto.CartItemRequest;
import com.swiftcart.entity.Cart;
import com.swiftcart.entity.Product;
import com.swiftcart.entity.User;
import com.swiftcart.repository.CartRepository;
import com.swiftcart.repository.ProductRepository;
import com.swiftcart.repository.UserRepository;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    private final CartRepository cartRepository;
    private final ProductRepository productRepository;
    private final UserRepository userRepository;

    public CartService(CartRepository cartRepository,
                      ProductRepository productRepository,
                      UserRepository userRepository) {
        this.cartRepository = cartRepository;
        this.productRepository = productRepository;
        this.userRepository = userRepository;
    }

    public List<CartItemDTO> getCartItems(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        return cartRepository.findByUser(user).stream()
                .map(this::convertToDTO)
                .collect(Collectors.toList());
    }

    public CartItemDTO addToCart(Long userId, CartItemRequest request) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));

        Product product = productRepository.findById(request.getProductId())
                .orElseThrow(() -> new RuntimeException("Product not found"));

        Cart existingCart = cartRepository.findByUserAndProductId(user, request.getProductId()).orElse(null);

        if (existingCart != null) {
            existingCart.setQuantity(existingCart.getQuantity() + request.getQuantity());
            cartRepository.save(existingCart);
            return convertToDTO(existingCart);
        }

        Cart cart = new Cart();
        cart.setUser(user);
        cart.setProduct(product);
        cart.setQuantity(request.getQuantity());

        Cart savedCart = cartRepository.save(cart);
        return convertToDTO(savedCart);
    }

    public CartItemDTO updateCartItem(Long cartId, Integer quantity) {
        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        cart.setQuantity(quantity);
        cartRepository.save(cart);
        return convertToDTO(cart);
    }

    public void removeFromCart(Long cartId) {
        cartRepository.deleteById(cartId);
    }

    public void clearCart(Long userId) {
        User user = userRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("User not found"));
        cartRepository.deleteByUser(user);
    }

    public BigDecimal getCartTotal(Long userId) {
        List<CartItemDTO> items = getCartItems(userId);
        return items.stream()
                .map(CartItemDTO::getTotal)
                .reduce(BigDecimal.ZERO, BigDecimal::add);
    }

    private CartItemDTO convertToDTO(Cart cart) {
        return new CartItemDTO(
                cart.getId(),
                cart.getProduct().getId(),
                cart.getProduct().getName(),
                cart.getProduct().getPrice(),
                cart.getQuantity(),
                cart.getProduct().getImageUrl(),
                cart.getProduct().getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()))
        );
    }
}


