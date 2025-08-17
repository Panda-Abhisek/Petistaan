package com.abhishekvermaa10.dto;

import java.time.LocalDateTime;

import org.springframework.http.HttpStatus;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class ErrorDTO {
	 private String message;
	 private Integer status;
	 private HttpStatus error;
	 private LocalDateTime timestamp;
}
