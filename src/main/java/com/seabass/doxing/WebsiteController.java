package com.seabass.doxing;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebsiteController {

	@GetMapping("/")
	String myMethod() {
		return "index.html";
	}
	@GetMapping("/results")
	String resultsMethod() {
		return "results.html";
	}
	
}
