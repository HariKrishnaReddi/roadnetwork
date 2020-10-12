package com.example.roadnetwork.domain;

import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Domain class represents a City along with the connected cities. City names
 * are not case sensitive.
 */
public class City {

	/* City name. */
	private String name;

	/* List of cities that are connected to this city. */
	private Set<City> connected = new HashSet<>();

	private City() {
		throw new UnsupportedOperationException();
	}

	private City(String name) {
		Objects.requireNonNull(name);
		this.name = name.trim().toUpperCase();
	}

	public static City build(String name) {
		return new City(name);
	}

	/**
	 * Return the name of the city.
	 * 
	 * @return {@link String} City name.
	 */
	public String getName() {
		return name;
	}

	public void setName(String name) {
		this.name = name;
	}

	public City addConnected(City city) {
		connected.add(city);
		return this;
	}

	public Set<City> getConnected() {
		return connected;
	}

	/**
	 * Find if destination city is connected to the origin.
	 * 
	 * @param destination the destination city
	 * @return true if cities are connected else false
	 */
	public boolean isConnected(City destination) {

		boolean connected = false;

		if (this.equals(destination) || this.getConnected().contains(destination)) {
			connected = true;
		}
		return connected;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("City [name=").append(name).append(", connected=")
				.append(connected.stream().map(City::getName).collect(Collectors.joining(","))).append("]");
		return builder.toString();
	}

	@Override
	public boolean equals(Object o) {
		if (this == o)
			return true;
		if (!(o instanceof City))
			return false;
		City city = (City) o;
		return Objects.equals(name, city.name);
	}

	@Override
	public int hashCode() {
		return Objects.hash(name);
	}
}
