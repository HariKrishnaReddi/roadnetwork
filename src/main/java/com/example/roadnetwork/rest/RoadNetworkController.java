package com.example.roadnetwork.rest;
 
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.example.roadnetwork.domain.City;
import com.example.roadnetwork.service.CityMapService;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiParam;

@RestController
@Api(value = "Road network controller")
public class RoadNetworkController {

	@Autowired
	private CityMapService cityMapService;

	@GetMapping(value = "/connected", produces = "text/plain")
	String findConnectivity(@ApiParam(name = "origin", value = "Origin City name", required = true) @RequestParam String origin,
			@ApiParam(name = "destination", value = "Destination City name", required = true) @RequestParam String destination) {
		City originCity = cityMapService.getCity(origin.toUpperCase());
		City destCity = cityMapService.getCity(destination.toUpperCase());

		
		if(originCity.isConnected( destCity)) {
			return "yes";
		} else {
			return "no";
		}
	}

}