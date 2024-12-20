package com.example.New_bazario.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
	public class HomeController {

	    @GetMapping("/")
	    public String home() {
	        return "home"; // This serves the home.html template
	    }
	}


