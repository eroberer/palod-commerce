package com.palod.commerce.domain.product.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.palod.commerce.domain.product.entity.Category;

@Repository
public interface CategoryRepository extends JpaRepository<Category, Long> {

}
