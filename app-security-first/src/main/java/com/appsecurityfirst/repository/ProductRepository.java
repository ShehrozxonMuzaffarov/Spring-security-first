package com.appsecurityfirst.repository;

import com.appsecurityfirst.entity.Product;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ProductRepository extends JpaRepository<Product, Long> {
}
