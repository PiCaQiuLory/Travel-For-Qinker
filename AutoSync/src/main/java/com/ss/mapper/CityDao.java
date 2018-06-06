package com.ss.mapper;

import com.ss.pojo.trip.City;
import com.ss.pojo.trip.Country;
import com.ss.pojo.trip.States;

public interface CityDao {

	void insertCountry(Country country);
	void insertStates(States states);
	void insertCity(City city);
	Country getCountryByName(String name);
	States getStatesByName(String name);
	City getCityByName(String name);
	
	String getCountryById(int id);
	String getStateById(int id);
	String getCityById(int id);
	States getStateByCid(int id);
}
