package com.example.roadnetwork.service;

import static org.junit.Assert.assertEquals;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.NoSuchElementException;
import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;

import com.example.roadnetwork.domain.City;

/**
 * Unit test CityMapService with mocked dependencies
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = CityMapService.class)
public class CityMapServiceTest {

	@Autowired
	@InjectMocks
	private CityMapService cityMapService;
	@Mock
	ResourceLoader resourceLoader;

	@Before
	public void setUp() throws Exception {
		String mockFile = "Boston, New York\nPhiladelphia, Newark\nNewark, Boston";
		InputStream is = new ByteArrayInputStream(mockFile.getBytes());
		Resource mockResource = Mockito.mock(Resource.class);
		Mockito.when(mockResource.getInputStream()).thenReturn(is);
		Mockito.when(resourceLoader.getResource(Mockito.anyString())).thenReturn(mockResource);
	}

	@Test(expected = NoSuchElementException.class)
	public void emptyCityName() {
		Optional<City> actualResult1 = cityMapService.getCity("");
		assertEquals("BOSTON", actualResult1.get().getName());
	}

	@Test
	public void getValidCityName() throws IOException {
		Optional<City> actualResult1 = cityMapService.getCity("BOSTON");
		assertEquals("BOSTON", actualResult1.get().getName());
	}
}
