package com.palod.commerce.domain.product.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import com.palod.commerce.domain.product.entity.Product;

@Repository
public interface ProductRepository extends JpaRepository<Product, Long> {
	
	@Query(value = "SELECT p FROM Product p WHERE p.category.id = :categoryId")
	public List<Product> findByCategoryId(Long categoryId);

}
