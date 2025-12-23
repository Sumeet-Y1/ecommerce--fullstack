package com.aureumpicks.ecommerce.service;

import com.aureumpicks.ecommerce.dto.CartRequest;
import com.aureumpicks.ecommerce.dto.CartResponse;
import com.aureumpicks.ecommerce.model.Cart;
import com.aureumpicks.ecommerce.model.Product;
import com.aureumpicks.ecommerce.model.User;
import com.aureumpicks.ecommerce.repository.CartRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.math.BigDecimal;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class CartService {

    @Autowired
    private CartRepository cartRepository;

    @Autowired
    private UserService userService;

    @Autowired
    private ProductService productService;

    // Get user's cart
    public List<CartResponse> getUserCart(String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        List<Cart> carts = cartRepository.findByUser(user);

        return carts.stream().map(this::convertToCartResponse).collect(Collectors.toList());
    }

    // Add to cart
    @Transactional
    public CartResponse addToCart(String email, CartRequest request) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Product product = productService.getProductById(request.getProductId());

        // Check if product already in cart
        Cart existingCart = cartRepository.findByUserAndProductId(user, product.getId()).orElse(null);

        if (existingCart != null) {
            // Update quantity
            existingCart.setQuantity(existingCart.getQuantity() + request.getQuantity());
            Cart updatedCart = cartRepository.save(existingCart);
            return convertToCartResponse(updatedCart);
        } else {
            // Add new cart item
            Cart cart = new Cart();
            cart.setUser(user);
            cart.setProduct(product);
            cart.setQuantity(request.getQuantity());
            Cart savedCart = cartRepository.save(cart);
            return convertToCartResponse(savedCart);
        }
    }

    // Update cart item quantity
    @Transactional
    public CartResponse updateCartItem(String email, Long cartId, Integer quantity) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Verify cart belongs to user
        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart");
        }

        cart.setQuantity(quantity);
        Cart updatedCart = cartRepository.save(cart);
        return convertToCartResponse(updatedCart);
    }

    // Remove from cart
    @Transactional
    public void removeFromCart(String email, Long cartId) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        Cart cart = cartRepository.findById(cartId)
                .orElseThrow(() -> new RuntimeException("Cart item not found"));

        // Verify cart belongs to user
        if (!cart.getUser().getId().equals(user.getId())) {
            throw new RuntimeException("Unauthorized access to cart");
        }

        cartRepository.delete(cart);
    }

    // Clear cart
    @Transactional
    public void clearCart(String email) {
        User user = userService.findByEmail(email);
        if (user == null) {
            throw new RuntimeException("User not found");
        }

        cartRepository.deleteByUser(user);
    }

    // Convert Cart entity to CartResponse DTO
    private CartResponse convertToCartResponse(Cart cart) {
        Product product = cart.getProduct();
        BigDecimal totalPrice = product.getPrice().multiply(BigDecimal.valueOf(cart.getQuantity()));

        return new CartResponse(
                cart.getId(),
                product.getId(),
                product.getName(),
                product.getImageUrl(),
                product.getPrice(),
                cart.getQuantity(),
                totalPrice
        );
    }
}