package com.example.roadnetwork.domain;

import static org.junit.Assert.*;
import org.junit.Test;

import java.lang.reflect.Constructor;
import java.lang.reflect.Modifier;
import java.util.Set;

public class CityTest {

	@Test()
	public void privateConstructor() {
		final Constructor<?>[] constructors = City.class.getDeclaredConstructors();
		for (Constructor<?> constructor : constructors) {
			assertTrue(Modifier.isPrivate(constructor.getModifiers()));
		}
	}

	@Test
	public void equals() {
		City nycCity = City.build("New York");
		City bostonCity = City.build("Boston");

		assertTrue(nycCity.equals(nycCity));
		assertFalse(nycCity.equals(bostonCity));
	}

	@Test
	public void build() {
		City city = City.build("New York");
		assertEquals("NEW YORK", city.getName());
	}

	@Test
	public void buildWithNearby() {
		City city = City.build("New York");
		city.addNearby(City.build("Boston")).addNearby(City.build("Albany"));

		Set<City> nearbyCities = city.getNearby();
		assertEquals(2, nearbyCities.size());
		assertTrue(nearbyCities.contains(City.build("Albany")));
	}

	@Test
	public void buildWithConnected() {
		City city = City.build("New York");
		city.addConnection(City.build("Boston")).addConnection(City.build("Albany"));

		Set<City> connectedCities = city.getConnections();
		assertEquals(2, connectedCities.size());
		assertTrue(connectedCities.contains(City.build("Albany")));
	}

	@Test
	public void addNearbyCities() {
		City city = City.build("Albany");
		city.addNearby(City.build("Boston")).addNearby(City.build("New York"));

		assertEquals(2, city.getNearby().size());
	}

	@Test
	public void addConnectedCities() {
		City city = City.build("Albany");
		city.addConnection(City.build("Boston")).addConnection(City.build("New York"));

		assertEquals(2, city.getConnections().size());
	}

	@Test
	public void addNearbyCitesWithDuplicateCities() {
		City city = City.build("New York");
		city.addNearby(City.build("Boston")).addNearby(City.build("BOSTON")).addNearby(City.build("  boSTOn"));

		assertEquals(1, city.getNearby().size());
	}

	@Test
	public void addConnectedCitesWithDuplicateCities() {
		City city = City.build("New York");
		city.addConnection(City.build("Boston")).addConnection(City.build("BOSTON"))
				.addConnection(City.build("  boSTOn"));

		assertEquals(1, city.getConnections().size());
	}

	@Test
	public void isConnected() {
		City city = City.build("New York");
		city.addNearby(City.build("Boston")).addNearby(City.build("Albany"));

		assertTrue(city.isConnected(City.build("New York")));
		assertTrue(city.isConnected(City.build("Albany")));
		assertFalse(city.isConnected(City.build("Trenton")));

		city.addConnection(City.build("Dover")).addConnection(City.build("Delaware"));

		assertTrue(city.isConnected(City.build("Dover")));
		assertTrue(city.isConnected(City.build("Delaware")));
	}

}
