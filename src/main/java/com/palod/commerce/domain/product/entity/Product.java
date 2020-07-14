package com.palod.commerce.domain.product.entity;

import java.math.BigDecimal;
import java.util.List;

import javax.persistence.CascadeType;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.OneToMany;
import javax.persistence.Table;

import com.palod.commerce.domain.core.entity.BaseEntity;
import com.palod.commerce.domain.user.entity.User;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "product")
@EqualsAndHashCode(callSuper = false)
public class Product extends BaseEntity {

	@Column(name = "name")
	private String name;

	@Column(name = "description")
	private String description;

	@Column(name = "amount")
	private BigDecimal amount;

	@Column(name = "quantity")
	private Long quantity;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "category_id", nullable = false)
	private Category category;

	@OneToMany(mappedBy = "product", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
	private List<ProductImage> productImageList;

	@ManyToOne(fetch = FetchType.LAZY)
	@JoinColumn(name = "created_by", nullable = false)
	private User createdBy;

}
