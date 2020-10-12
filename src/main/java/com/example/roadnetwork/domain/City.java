package com.example.roadnetwork.domain;

import java.util.Objects;

public class City {

	private String name;

	public boolean isConnected(City destCity) {
		return false;
	}
	
	public City(String name) {
		Objects.requireNonNull(name);
		this.name = name.trim().toUpperCase();
	}

}
