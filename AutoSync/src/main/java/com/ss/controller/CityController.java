package com.ss.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import com.ss.service.CityService;

@Controller
public class CityController {

	@Autowired
	private CityService cityService;
	
	@RequestMapping("/insertCity")
	@ResponseBody
	public String insert() throws Exception {
		cityService.insertCity();
		return "success";
	}
}
