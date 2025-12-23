package com.aureumpicks.ecommerce.controller;

import com.aureumpicks.ecommerce.dto.CartRequest;
import com.aureumpicks.ecommerce.dto.CartResponse;
import com.aureumpicks.ecommerce.dto.MessageResponse;
import com.aureumpicks.ecommerce.service.CartService;
import jakarta.validation.Valid;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/cart")
@CrossOrigin(origins = "*")
public class CartController {

    @Autowired
    private CartService cartService;

    // Get current user's email from JWT token
    private String getCurrentUserEmail() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        return authentication.getName();
    }

    // Get user's cart
    @GetMapping
    public ResponseEntity<?> getUserCart() {
        try {
            String email = getCurrentUserEmail();
            List<CartResponse> cart = cartService.getUserCart(email);
            return ResponseEntity.ok(cart);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error fetching cart: " + e.getMessage()));
        }
    }

    // Add to cart
    @PostMapping("/add")
    public ResponseEntity<?> addToCart(@Valid @RequestBody CartRequest request) {
        try {
            String email = getCurrentUserEmail();
            CartResponse cartItem = cartService.addToCart(email, request);
            return ResponseEntity.status(HttpStatus.CREATED).body(cartItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error adding to cart: " + e.getMessage()));
        }
    }

    // Update cart item quantity
    @PutMapping("/update/{cartId}")
    public ResponseEntity<?> updateCartItem(@PathVariable Long cartId, @RequestParam Integer quantity) {
        try {
            String email = getCurrentUserEmail();
            CartResponse cartItem = cartService.updateCartItem(email, cartId, quantity);
            return ResponseEntity.ok(cartItem);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error updating cart: " + e.getMessage()));
        }
    }

    // Remove from cart
    @DeleteMapping("/remove/{cartId}")
    public ResponseEntity<?> removeFromCart(@PathVariable Long cartId) {
        try {
            String email = getCurrentUserEmail();
            cartService.removeFromCart(email, cartId);
            return ResponseEntity.ok(new MessageResponse("Item removed from cart"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error removing from cart: " + e.getMessage()));
        }
    }

    // Clear cart
    @DeleteMapping("/clear")
    public ResponseEntity<?> clearCart() {
        try {
            String email = getCurrentUserEmail();
            cartService.clearCart(email);
            return ResponseEntity.ok(new MessageResponse("Cart cleared successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error clearing cart: " + e.getMessage()));
        }
    }
}