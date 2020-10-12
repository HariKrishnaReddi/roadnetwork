package com.example.roadnetwork.rest;

import static org.junit.Assert.assertNotNull;
import static org.mockito.Mockito.doReturn;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.content;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

import java.lang.reflect.Method;
import java.util.Optional;

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
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.method.annotation.ExceptionHandlerMethodResolver;
import org.springframework.web.servlet.mvc.method.annotation.ExceptionHandlerExceptionResolver;
import org.springframework.web.servlet.mvc.method.annotation.ServletInvocableHandlerMethod;
import org.springframework.web.util.NestedServletException;

import com.example.roadnetwork.domain.City;
import com.example.roadnetwork.exception.CityNotFoundException;
import com.example.roadnetwork.exception.ExceptionControllerAdvice;
import com.example.roadnetwork.service.CityMapService;

/**
 * Unit test RoadNetworkController with mocked dependencies
 */
@RunWith(MockitoJUnitRunner.class)
public class RoadNetworkControllerTest {

	@Autowired
	private MockMvc mockMvc;

	@Mock
	private CityMapService service;

	@Autowired
	@InjectMocks
	private RoadNetworkController beanUnderTest;

	@Before
	public void setUp() {
		mockMvc = MockMvcBuilders.standaloneSetup(beanUnderTest).setHandlerExceptionResolvers(createExceptionResolver())
				.build();

		City origin = City.build("origin");
		City destination = City.build("destination");

		doReturn(Optional.of(origin)).when(service).getCity("ORIGIN");
		doReturn(Optional.of(destination)).when(service).getCity("DESTINATION");
		doReturn(Optional.empty()).when(service).getCity("INVALID");
	}

	@Test(expected = CityNotFoundException.class)
	public void invalidSourceCity() throws Exception {

		try {
			mockMvc.perform(get("/connected").param("origin", "INVALID").param("destination", "destination"))
					.andExpect(status().isOk()).andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
					.andReturn();
		} catch (NestedServletException exception) {
			throw (Exception) exception.getCause();
		}
	}

	@Test(expected = CityNotFoundException.class)
	public void invalidDestinationCity() throws Exception {

		try {
			mockMvc.perform(get("/connected").param("origin", "origin").param("destination", "INVALID"))
					.andExpect(status().isOk()).andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
					.andReturn();
		} catch (NestedServletException exception) {
			throw (Exception) exception.getCause();
		}
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

	@Test
	public void findConnectivityToSameCity() throws Exception {
		MvcResult result = mockMvc.perform(get("/connected").param("origin", "origin").param("destination", "origin"))
				.andExpect(status().isOk()).andExpect(content().contentType("text/plain;charset=ISO-8859-1"))
				.andReturn();
		assertNotNull(result);

		Mockito.verify(service, Mockito.times(2)).getCity("ORIGIN");
	}

	private ExceptionHandlerExceptionResolver createExceptionResolver() {
		ExceptionHandlerExceptionResolver exceptionResolver = new ExceptionHandlerExceptionResolver() {
			protected ServletInvocableHandlerMethod getExceptionHandlerMethod(HandlerMethod handlerMethod,
					Exception exception) {
				Method method = new ExceptionHandlerMethodResolver(ExceptionControllerAdvice.class)
						.resolveMethod(exception);
				return new ServletInvocableHandlerMethod(new ExceptionControllerAdvice(), method);
			}
		};
		exceptionResolver.afterPropertiesSet();
		return exceptionResolver;
	}

}
