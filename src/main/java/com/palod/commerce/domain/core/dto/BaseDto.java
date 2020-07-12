package com.palod.commerce.domain.core.dto;

import java.time.LocalDateTime;

import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public abstract class BaseDto {

	private Long id;
	
	private LocalDateTime createdDate;
}
