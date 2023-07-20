package com.example.api.controller;

import com.example.ulti.CommonHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;


@RestController
public class ApiController {
	private static final Logger log = LoggerFactory.getLogger(ApiController.class);
	@GetMapping("/")
	public String index() {
		return "index";
	}

	@Autowired
	CommonHelper commonHelper;

}
