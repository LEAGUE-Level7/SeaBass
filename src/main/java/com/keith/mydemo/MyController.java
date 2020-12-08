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
		// "{threatlevel:" + new Random().nextInt(1000)+ "}";
	}

	@GetMapping("/twitterUser")
	String twitterUser(String username) {
		if (username == null) {
			username = "elonmusk";
		}
		try {
			return Twitter.showUser(username);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Something went wrong querying the twitter api";
	}

	@GetMapping("/twitterSearch")
	String twitterSearch(String searchContent) {
		if (searchContent == null) {
			searchContent = "keith";
		}
		try {
			return Twitter.showSearch(searchContent);
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
	String databaseTest() {
		return DatabaseTest.getAllData();
	}

	@GetMapping("/databaseTest2")
	String databaseTest2() {
		return DatabaseTest.putData();
	}
}
