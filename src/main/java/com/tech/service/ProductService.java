package com.tech.service;

import com.tech.dto.ProductResponseDTO;
import com.tech.model.Product;
import com.tech.repository.ProductRepository;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository) {
        this.productRepository = productRepository;
    }

    private ProductResponseDTO mapToDto(Product product) {
        ProductResponseDTO dto = new ProductResponseDTO();
        dto.setId(product.getId());
        dto.setName(product.getName());
        dto.setPrice(product.getPrice());
        dto.setStockQuantity(product.getStockQuantity());
        dto.setImageUrl(product.getImageUrl());
        dto.setCategoryName(product.getCategory() != null ? product.getCategory().getName() : null);
        dto.setShortDescription(product.getDescription() != null && product.getDescription().length() > 100 ?
                product.getDescription().substring(0, 100) + "..." : product.getDescription());
        dto.setFullDescription(product.getDescription());
        return dto;
    }

    public Page<ProductResponseDTO> getAllProducts(int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findAll(pageable).map(this::mapToDto);
    }

    public Optional<ProductResponseDTO> getProductById(Long id) {
        return productRepository.findById(id).map(this::mapToDto);
    }

    public Page<ProductResponseDTO> searchProducts(String keyword, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(keyword, keyword, pageable)
                .map(this::mapToDto);
    }

    public Page<ProductResponseDTO> filterProductsByCategory(String categoryName, int page, int size, String sortBy, String sortDir) {
        Sort sort = sortDir.equalsIgnoreCase(Sort.Direction.ASC.name()) ? Sort.by(sortBy).ascending() : Sort.by(sortBy).descending();
        Pageable pageable = PageRequest.of(page, size, sort);
        return productRepository.findByCategoryName(categoryName, pageable)
                .map(this::mapToDto);
    }

    public int getStockQuantity(Long productId) {
        return productRepository.findById(productId)
                .map(Product::getStockQuantity)
                .orElse(0);
    }
}
