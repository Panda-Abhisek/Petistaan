package com.abhishekvermaa10.exception;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.HttpRequestMethodNotSupportedException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import com.abhishekvermaa10.dto.ErrorDTO;

import jakarta.validation.ValidationException;

@RestControllerAdvice
public class GlobalExceptionHandler {
	
	@ExceptionHandler(NullPointerException.class)
	public ResponseEntity<ErrorDTO> handleNullPointerException(NullPointerException e) {
		ErrorDTO errorDTO = new ErrorDTO();
		errorDTO.setMessage(e.getMessage());
		errorDTO.setStatus(HttpStatus.BAD_REQUEST.value());
		errorDTO.setError(HttpStatus.BAD_REQUEST);
		errorDTO.setTimestamp(LocalDateTime.now());
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}
	
	@ExceptionHandler(OwnerNotFoundException.class)
	public ResponseEntity<ErrorDTO> handleOwnerNotFoundException(OwnerNotFoundException e) {
		ErrorDTO errorDTO = ErrorDTO.builder()
				.message(e.getMessage())
				.error(HttpStatus.NOT_FOUND)
				.status(HttpStatus.NOT_FOUND.value())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}
	
	@ExceptionHandler(PetNotFoundException.class)
	public ResponseEntity<ErrorDTO> handlePetNotFoundException(PetNotFoundException e) {
		ErrorDTO errorDTO = ErrorDTO.builder()
				.message(e.getMessage())
				.error(HttpStatus.NOT_FOUND)
				.status(HttpStatus.NOT_FOUND.value())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleValidationException(ValidationException e) {
		ErrorDTO errorDTO = ErrorDTO.builder()
				.message(e.getMessage())
				.error(HttpStatus.CONFLICT)
				.status(HttpStatus.CONFLICT.value())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleMethodNotAllowedException(HttpRequestMethodNotSupportedException e) {
		ErrorDTO errorDTO = ErrorDTO.builder()
				.message(e.getMessage())
				.error(HttpStatus.METHOD_NOT_ALLOWED)
				.status(HttpStatus.METHOD_NOT_ALLOWED.value())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}
	
	@ExceptionHandler
	public ResponseEntity<ErrorDTO> handleGenericException(Exception e) {
		ErrorDTO errorDTO = ErrorDTO.builder()
				.message(e.getMessage())
				.error(HttpStatus.INTERNAL_SERVER_ERROR)
				.status(HttpStatus.INTERNAL_SERVER_ERROR.value())
				.timestamp(LocalDateTime.now())
				.build();
		return ResponseEntity.status(errorDTO.getStatus()).body(errorDTO);
	}
	
}
