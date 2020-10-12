package com.example.roadnetwork.exception;

import static org.junit.Assert.*;

import org.junit.Test;

public class CityNotFoundExceptionTest {
	@Test
	public void constructors() {
		CityNotFoundException exception = new CityNotFoundException("test");
		assertNotNull(exception);
		assertEquals("City with name: test not found", exception.getMessage());
	}
}
