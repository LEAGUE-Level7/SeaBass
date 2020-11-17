package com.keith.mydemo;

import java.util.Random;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

@RestController
public class MyController {


	@PostMapping("/getScore")
	Threat myMethod(@RequestBody String str) {
		System.out.println("post request: " + str);
			return new Threat("email", "e@gmail.com");
					//"{threatlevel:" + new Random().nextInt(1000)+ "}";
		
	}
}
