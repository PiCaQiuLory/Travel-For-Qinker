package com.ss.service.impl;

import java.io.File;
import java.util.List;

import com.ss.mapper.CityDao;
import org.dom4j.Document;
import org.dom4j.Element;
import org.dom4j.io.SAXReader;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.ss.pojo.trip.City;
import com.ss.pojo.trip.Country;
import com.ss.pojo.trip.States;
import com.ss.service.CityService;

@Service
public class CityServiceImpl implements CityService{

	@Autowired
	private CityDao cityDao;

	@Override
	public void insertCity() throws Exception {
		SAXReader reader = new SAXReader();
		Document document = reader.read(new File("C:\\Users\\13162\\Desktop\\LocList.xml"));
		Element root = document.getRootElement();
		List<Element> elementCountry = root.elements("CountryRegion");
		for(Element countryEle:elementCountry) {
			Country country = new Country();
			country.setName(countryEle.attributeValue("Name"));
			cityDao.insertCountry(country);
			List<Element> stateElements = countryEle.elements("State");
			for(Element stateEle:stateElements) {
				States states = new States();
				states.setCo_id(country.getId());
				states.setName(stateEle.attributeValue("Name"));
				cityDao.insertStates(states);
				List<Element> cityElements = stateEle.elements("City");
				for(Element cityEle:cityElements) {
					City city = new City();
					city.setName(cityEle.attributeValue("Name"));
					city.setS_id(states.getId());
					cityDao.insertCity(city);
				}
			}
		}
	}
	
	

}
