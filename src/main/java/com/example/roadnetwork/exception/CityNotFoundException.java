package com.example.roadnetwork.exception;

/**
 * Class for catching an exception when the city is not found for the given city
 * name.
 *
 */
public class CityNotFoundException extends RuntimeException {

	private static final long serialVersionUID = -2086029724995132404L;

	public CityNotFoundException(String name) {
		super(String.format("City with name: %s not found", name));
	}
}
