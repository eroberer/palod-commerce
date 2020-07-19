package com.palod.commerce.domain.user.entity;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.Table;

import com.palod.commerce.domain.core.entity.BaseEntity;

import lombok.Data;
import lombok.EqualsAndHashCode;

@Data
@Entity
@Table(name = "role")
@EqualsAndHashCode(callSuper = false)
public class Role extends BaseEntity {

	@Column(name = "name")
	private String name;
}
