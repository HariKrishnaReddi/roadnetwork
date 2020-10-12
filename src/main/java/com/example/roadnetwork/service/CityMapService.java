package com.example.roadnetwork.service;

import org.springframework.stereotype.Service;

import com.example.roadnetwork.domain.City;

@Service
public class CityMapService {

	public City getCity(String name) {
		return new City(name);
	}

}
