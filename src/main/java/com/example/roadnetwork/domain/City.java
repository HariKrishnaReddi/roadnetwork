package com.example.roadnetwork.domain;

import java.util.ArrayDeque;
import java.util.Collections;
import java.util.Deque;
import java.util.HashSet;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

/**
 * Domain class represents a City along with the connected cities. City names
 * are not case sensitive.
 */
public class City {

	/* name of the city. */
	private String name;

	/* List of cities that are connected to this city. */
	private Set<City> nearby = new HashSet<>();

	/* List of All cities that are connected to this city. */
	private Set<City> connections = new HashSet<>();

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
	 * Adds the city to the list of connected cities of this city.
	 * 
	 * @param city name of the city that need to be added.
	 */
	public City addConnection(final City connection) {
		this.connections.add(connection);
		return this;
	}

	/**
	 * Adds the set of cities to the list of connected cities of this city.
	 * 
	 * @param cities Set of the cities that need to be added.
	 */
	public City addConnections(final Set<City> connections) {
		this.connections.addAll(connections);
		return this;
	}

	/**
	 * Return list of cities connected to this city.
	 * 
	 * @return {@link Set} of {@link City}s.
	 */
	public Set<City> getConnections() {
		return this.connections;
	}

	/**
	 * Check if the city node has connection with the target city.
	 * 
	 * @param city name of the city to check connection with.
	 * @return <code>true</code> if city is connected with target city,
	 *         <code>false</code> if not.
	 */
	public boolean hasConnectionWith(final City city) {
		if (connections.isEmpty()) {
			return false;
		} else {
			return connections.contains(city);
		}
	}

	public String getName() {
		return name;
	}

	public City addNearby(City city) {
		nearby.add(city);
		return this;
	}

	public Set<City> getNearby() {
		return nearby;
	}

	/**
	 * Find if destination city is reachable from origin. Will visit all the cities
	 * on the bucket list which is built by collecting all the neighbours of a
	 * visited place
	 * 
	 * @param destination the destination city
	 * @return true if cities are connected else false
	 */
	public boolean isConnected(City destination) {

		boolean connected = false;
		if (this.equals(destination) || this.getNearby().contains(destination) || this.hasConnectionWith(destination)) {
			connected = true;
		} else {

			// The origin city was already visited since we have started from it
			Set<City> visited = new HashSet<>(Collections.singleton(this));

			// Put all the neighboring cities into a bucket list
			Deque<City> bucketlist = new ArrayDeque<>(this.getNearby());

			while (!bucketlist.isEmpty()) {
				City city = bucketlist.getLast();
				if (city.equals(destination)) {
					connected = true;
					break;
				} else {
					// remove the city from the bucket list
					if (visited.contains(city)) {
						// the city has been visited, so remove it from the bucket list
						bucketlist.remove(city);
					} else {
						// Visiting the city for the first time.
						visited.add(city);
						bucketlist.addAll(city.getNearby());
						this.addConnections(city.getNearby());
						bucketlist.removeAll(visited);
					}
				}
			}
		}
		return connected;
	}

	@Override
	public String toString() {
		StringBuilder builder = new StringBuilder();
		builder.append("City [name=").append(name).append(", nearby=")
				.append(nearby.stream().map(City::getName).collect(Collectors.joining(","))).append(", connections=")
				.append(connections.stream().map(City::getName).collect(Collectors.joining(","))).append("]");
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
