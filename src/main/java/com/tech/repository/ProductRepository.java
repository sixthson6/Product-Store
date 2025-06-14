package com.tech.repository;


import com.tech.model.Product;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ProductRepository extends JpaRepository<Product, Long> {
    Page<Product> findByNameContainingIgnoreCaseOrDescriptionContainingIgnoreCase(String nameKeyword, String descriptionKeyword, Pageable pageable);

    Page<Product> findByCategoryName(String categoryName, Pageable pageable);

     List<Product> findByCategoryNameIn(List<String> categoryNames);
}
