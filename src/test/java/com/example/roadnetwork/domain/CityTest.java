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
	public void buildWithConnected() {
		City city = City.build("New York");
		city.addConnected(City.build("Boston")).addConnected(City.build("Albany"));

		Set<City> connectedCities = city.getConnected();
		assertEquals(2, connectedCities.size());
		assertTrue(connectedCities.contains(City.build("Albany")));
	}

	@Test
	public void addConnectedCities() {
		City city = City.build("Albany");
		city.addConnected(City.build("Boston")).addConnected(City.build("New York"));

		assertEquals(2, city.getConnected().size());
	}

	@Test
	public void addConnectedCitesWithDuplicateCities() {
		City city = City.build("New York");
		city.addConnected(City.build("Boston")).addConnected(City.build("BOSTON")).addConnected(City.build("  boSTOn"));

		assertEquals(1, city.getConnected().size());
	}

	@Test
	public void isConnected() {
		City city = City.build("New York");
		city.addConnected(City.build("Boston")).addConnected(City.build("Albany"));

		assertTrue(city.isConnected(City.build("New York")));
		assertTrue(city.isConnected(City.build("Albany")));
		assertFalse(city.isConnected(City.build("Trenton")));
	}

}
