package com.palod.commerce.api.handler;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import com.palod.commerce.api.exception.ApiException;
import com.palod.commerce.api.response.ApiErrorResponse;

@ControllerAdvice
public class ApiExceptionHandler extends ResponseEntityExceptionHandler {

	@ExceptionHandler({ ApiException.class })
	protected ResponseEntity<Object> handleAuthException(HttpServletRequest request, HttpServletResponse response, ApiException ex) {
		
		ApiErrorResponse apiResponse = new ApiErrorResponse();
		apiResponse.setStatus(ex.getStatus());
		apiResponse.setErrorList(Arrays.asList(ex.getMessage()));

		return new ResponseEntity<>(apiResponse, HttpStatus.BAD_REQUEST);
	}

	@Override
	protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
			HttpHeaders headers, HttpStatus status, WebRequest request) {

		ApiErrorResponse response = new ApiErrorResponse();
		response.setStatus(status.value());
		response.setErrorList(getArgumentValidationErrorList(ex));

		return new ResponseEntity<>(response, headers, status);
	}

	private List<String> getArgumentValidationErrorList(MethodArgumentNotValidException ex) {
		return ex.getBindingResult().getFieldErrors().stream()
				.map(argError -> argError.getField() + " " + argError.getDefaultMessage()).collect(Collectors.toList());
	}
}
