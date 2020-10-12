package com.example.roadnetwork.rest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.Mockito;
import org.mockito.junit.MockitoJUnitRunner;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;

import com.example.roadnetwork.domain.City;
import com.example.roadnetwork.service.CityMapService;

/**
 * Unit test RoadNetworkController with mocked dependencies
 */
@RunWith(MockitoJUnitRunner.class)
public class RoadNetworkControllerTest {
	private MockMvc mockMvc;

	@Mock
	private CityMapService service;

	@Autowired
	@InjectMocks
	private RoadNetworkController beanUnderTest;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(beanUnderTest).build();

		City origin = City.build("origin");
		City destination = City.build("destination");

		doReturn(origin).when(service).getCity("ORIGIN");
		doReturn(destination).when(service).getCity("DESTINATION");
	}

	@Test
	public void findConnectivity() throws Exception {
		MvcResult result = mockMvc
				.perform(get("/connected").param("origin", "origin").param("destination", "destination"))
				.andExpect(status().isOk()).andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
				.andReturn();

		assertNotNull(result);

		Mockito.verify(service, Mockito.times(1)).getCity("ORIGIN");
		Mockito.verify(service, Mockito.times(1)).getCity("DESTINATION");
	}

}
