package com.keith.mydemo;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class WebController {

	@GetMapping("/page")
	String myMethod() {
		return "index.html";
	}

	@GetMapping("/test")
	String redirectMethod() {
		return "redirect.html";
	}
}
