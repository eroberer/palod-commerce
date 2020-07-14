package com.palod.commerce.domain.core.dto;

import java.io.Serializable;
import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseDto implements Serializable {

	private static final long serialVersionUID = 9101762958322959125L;

	private Long id;

	private LocalDateTime createdDate;
}
