package com.example.roadnetwork.exception;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotNull;
import org.junit.Test;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

public class ExceptionControllerAdviceTest {

	private ExceptionControllerAdvice advice = new ExceptionControllerAdvice();

	@Test
	public void handleNotFoundException() {

		ResponseEntity<ErrorResponse> result = advice.handleCityNotFoundException(new CityNotFoundException("London"));
		assertNotNull(result);
		assertEquals(HttpStatus.NOT_FOUND, result.getStatusCode());
		assertEquals("City with name: London not found", result.getBody().getMessage());
	}
}
