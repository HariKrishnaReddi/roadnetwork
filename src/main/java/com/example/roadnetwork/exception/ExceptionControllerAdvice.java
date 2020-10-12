package com.example.roadnetwork.exception;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

/**
 * Global exception handler for all rest end points
 */
@ControllerAdvice
public class ExceptionControllerAdvice {
	private final Log LOG = LogFactory.getLog(ExceptionControllerAdvice.class);

	@ExceptionHandler(CityNotFoundException.class)
	public ResponseEntity<String> handleCityNotFoundException(CityNotFoundException ex) {
		LOG.error("Not Found Exception", ex);
		return new ResponseEntity<>(ex.getMessage(), HttpStatus.NOT_FOUND);
	}
}
