package com.keith.mydemo;

import java.io.*;
import java.net.*;

import org.json.*;
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

	@GetMapping("/twitterTest2")
	String twitterTest2(String ids) {
		if (ids == null) {
			ids = "20";
		}
		try {
			return Twitter.dotweet(ids);
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return "Something went wrong querying the twitter api";
	}

	@GetMapping("/getlatesttweet")
	String getLatestTweet(String user) {
		user = "elonmusk";
		if (user == null) {
			return "Specify a user, dummy!";
		}
		try {
			// This parses the json of the tweet results
			String ret = "tweets:\n";
			JSONObject jsonobj = new JSONObject(Twitter.getLatest(user));
			JSONArray tweetlist = jsonobj.getJSONArray("data");
			for (int i = 0; i < tweetlist.length(); i++) {
				System.out.println(tweetlist.get(i));
				ret += "tweet " + i + ": " + tweetlist.get(i) + "\n";
			}
			for (String key : jsonobj.keySet()) {
				System.out.println(jsonobj.get(key));
			}
			return ret;
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
	String databaseTest2(String number) {
		if (number == null) {
			return DatabaseTest.putData();
		}
		return DatabaseTest.putSomeData(number);
	}
	
	@GetMapping("/filterStreams")
	void filterStream() {
		try {
			Twitter.showFilteredStream();
		} catch (IOException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		} catch (URISyntaxException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
	}
}
