package com.example.roadnetwork.service;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;

/**
 * Unit test CityMapServiceTest with mocked dependencies
 */
@RunWith(MockitoJUnitRunner.class)
public class CityMapServiceTest {
	
	@Autowired
	@InjectMocks
	private CityMapService cityMapService;

	@Test
	public void getCity() {
		cityMapService.getCity("");
	}
}
