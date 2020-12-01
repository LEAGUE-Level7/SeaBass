package com.keith.mydemo;

import java.io.*;
import java.net.*;

import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {
	
	public MyController() {
		DatabaseTest.initializeConnection();
	}

	@PostMapping("/getScore")
	Threat myMethod(@RequestBody String str) {
		System.out.println("post request: " + str);
			return new Threat("email", "e@gmail.com");
					//"{threatlevel:" + new Random().nextInt(1000)+ "}";
	}
	
	@GetMapping("/twitterTest")
	String twitterTest(String username) {
		if(username == null) {
			username = "TwitterEng";
		}
		try {
			return Twitter.dostuff(username);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Something went wrong querying the twitter api";
	}
	@GetMapping("/databaseTest") 
	String databaseTest(String param) {
		return DatabaseTest.getAllData();
	}
}
