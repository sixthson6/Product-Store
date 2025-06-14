package com.tech.controller;

import com.tech.dto.ProductResponseDTO;
import com.tech.service.ProductService;
import org.springframework.data.domain.Page;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/products")
public class ProductController {

    private final ProductService productService;

    public ProductController(ProductService productService) {
        this.productService = productService;
    }

    @GetMapping
    public ResponseEntity<Page<ProductResponseDTO>> getAllProducts(
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<ProductResponseDTO> products = productService.getAllProducts(page, size, sortBy, sortDir);
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ProductResponseDTO> getProductById(@PathVariable Long id) {
        return productService.getProductById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/search")
    public ResponseEntity<Page<ProductResponseDTO>> searchProducts(
            @RequestParam String keyword,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<ProductResponseDTO> products = productService.searchProducts(keyword, page, size, sortBy, sortDir);
        if (products.isEmpty() && !keyword.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Or HttpStatus.OK with empty content
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/category/{categoryName}")
    public ResponseEntity<Page<ProductResponseDTO>> filterProductsByCategory(
            @PathVariable String categoryName,
            @RequestParam(defaultValue = "0") int page,
            @RequestParam(defaultValue = "10") int size,
            @RequestParam(defaultValue = "name") String sortBy,
            @RequestParam(defaultValue = "asc") String sortDir
    ) {
        Page<ProductResponseDTO> products = productService.filterProductsByCategory(categoryName, page, size, sortBy, sortDir);
        if (products.isEmpty() && !categoryName.isEmpty()) {
            return new ResponseEntity<>(HttpStatus.NO_CONTENT); // Or HttpStatus.OK with empty content
        }
        return ResponseEntity.ok(products);
    }

    @GetMapping("/{id}/stock")
    public ResponseEntity<Integer> getProductStockQuantity(@PathVariable Long id) {
        int stock = productService.getStockQuantity(id);
        if (stock == 0 && !productService.getProductById(id).isPresent()) {
            return ResponseEntity.notFound().build(); // Product not found
        }
        return ResponseEntity.ok(stock);
    }
}