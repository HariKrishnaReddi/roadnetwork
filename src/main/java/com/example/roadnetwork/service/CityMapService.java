package com.example.roadnetwork.service;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import java.util.Objects;
import java.util.Optional;
import java.util.Scanner;

import javax.annotation.PostConstruct;

import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import com.example.roadnetwork.domain.City;

/**
 * Build a road map from the text file. Ensure names are not case sensitive. The
 * application will fail fast if data file is not readable or invalid White
 * spaces and empty lines ignored
 */
@Service
public class CityMapService {

	private final Log LOG = LogFactory.getLog(CityMapService.class);

	private Map<String, City> cityMap = new HashMap<>();

	@Value("${data.file:classpath:routes.txt}")
	private String ROUTES;

	@Autowired
	private ResourceLoader resourceLoader;

	/**
	 * Loads the routes file into a Map
	 * 
	 * @throws IOException
	 */
	@PostConstruct
	private void read() throws IOException {

		LOG.debug("Reading data");

		Resource resource = resourceLoader.getResource(ROUTES);

		InputStream is = resource.getInputStream();
		try (Scanner scanner = new Scanner(is)) {

			while (scanner.hasNext()) {

				String line = scanner.nextLine();
				if (StringUtils.isEmpty(line))
					continue;

				LOG.debug(line);

				String[] split = line.split(",");
				String firstCityStr = split[0].trim().toUpperCase();
				String secondCityStr = split[1].trim().toUpperCase();

				if (!firstCityStr.equals(secondCityStr)) {
					City firstCity = cityMap.getOrDefault(firstCityStr, City.build(firstCityStr));
					City secondCity = cityMap.getOrDefault(secondCityStr, City.build(secondCityStr));

					firstCity.addNearby(secondCity);
					secondCity.addNearby(firstCity);

					firstCity.addConnection(secondCity);
					secondCity.addConnection(firstCity);

					cityMap.put(firstCity.getName(), firstCity);
					cityMap.put(secondCity.getName(), secondCity);
				}
			}
		}

		LOG.debug("Map: " + cityMap);
	}

	public Optional<City> getCity(String name) {
		Objects.requireNonNull(name);
		return Optional.ofNullable(cityMap.get(name));
	}
}
