package com.keith.mydemo;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {
	

	
	@PostMapping("/post")
	String myMethod(@RequestBody String str) {
		System.out.println("hello you have made a request");
		return str;
	}
}
