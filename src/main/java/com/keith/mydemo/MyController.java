package com.keith.mydemo;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {

	@GetMapping("/")
	String myMethod() {
		return "hello";
	}

	@PostMapping("/")
	String myMethod(@RequestBody String str) {
		System.out.println("post request: " + str);
		if (str.trim().equalsIgnoreCase("getScore")) {
			
			return "" + new Random().nextInt(1000);
		} else {
			return "" + -1;
		}
	}
}
