package com.aureumpicks.ecommerce.controller;

import com.aureumpicks.ecommerce.dto.MessageResponse;
import com.aureumpicks.ecommerce.model.Product;
import com.aureumpicks.ecommerce.service.ProductService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/products")
@CrossOrigin(origins = "*")
public class ProductController {

    @Autowired
    private ProductService productService;

    // Get all products
    @GetMapping("/all")
    public ResponseEntity<?> getAllProducts() {
        try {
            List<Product> products = productService.getAllProducts();
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error fetching products: " + e.getMessage()));
        }
    }

    // Get product by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getProductById(@PathVariable Long id) {
        try {
            Product product = productService.getProductById(id);
            return ResponseEntity.ok(product);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(new MessageResponse(e.getMessage()));
        }
    }

    // Get products by category
    @GetMapping("/category/{category}")
    public ResponseEntity<?> getProductsByCategory(@PathVariable String category) {
        try {
            List<Product> products = productService.getProductsByCategory(category);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error fetching products: " + e.getMessage()));
        }
    }

    // Search products
    @GetMapping("/search")
    public ResponseEntity<?> searchProducts(@RequestParam String name) {
        try {
            List<Product> products = productService.searchProducts(name);
            return ResponseEntity.ok(products);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR)
                    .body(new MessageResponse("Error searching products: " + e.getMessage()));
        }
    }

    // Add new product (Admin only - you can add @PreAuthorize later)
    @PostMapping("/add")
    public ResponseEntity<?> addProduct(@RequestBody Product product) {
        try {
            Product savedProduct = productService.addProduct(product);
            return ResponseEntity.status(HttpStatus.CREATED).body(savedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error adding product: " + e.getMessage()));
        }
    }

    // Update product (Admin only)
    @PutMapping("/update/{id}")
    public ResponseEntity<?> updateProduct(@PathVariable Long id, @RequestBody Product product) {
        try {
            Product updatedProduct = productService.updateProduct(id, product);
            return ResponseEntity.ok(updatedProduct);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error updating product: " + e.getMessage()));
        }
    }

    // Delete product (Admin only)
    @DeleteMapping("/delete/{id}")
    public ResponseEntity<?> deleteProduct(@PathVariable Long id) {
        try {
            productService.deleteProduct(id);
            return ResponseEntity.ok(new MessageResponse("Product deleted successfully"));
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(new MessageResponse("Error deleting product: " + e.getMessage()));
        }
    }
}