package com.example.roadnetwork;

import org.junit.jupiter.api.Test;
import org.junit.runner.RunWith;
import org.springframework.test.annotation.DirtiesContext;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DirtiesContextTestExecutionListener;
import org.springframework.test.context.web.WebAppConfiguration;

/**
 *
 */
@RunWith(SpringJUnit4ClassRunner.class)
@ContextConfiguration(classes = RoadnetworkApplication.class)
@WebAppConfiguration
@DirtiesContext
@TestExecutionListeners({ DirtiesContextTestExecutionListener.class })
public class RoadnetworkApplicationTests {
	@Test
	public void emptySpringContextLoadTest() {
	}
}