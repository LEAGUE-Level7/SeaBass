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

	Threat myMethod(@RequestBody String body) {
		System.out.println("post request: " + body);
		JSONObject jsonobj = new JSONObject(body);
		String username = jsonobj.getString("username");
		
		int threatLevel = 0;
		Threat threat = new Threat();
		
		boolean exists = true; //Twitter.doesAccountExist(username);
		if(exists) {
			threatLevel = 1;
			String result = getLatestTweet(username);
			threat.setLatestTweet(result);
		}
		else {
		threat.setMessage("Account does not exist!");
		}
		
		threat.setUsername(username);
		threat.setThreatLevel(threatLevel);
		
		DatabaseTest.putSomeData(threatLevel);
		
		return threat;
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

	@GetMapping("/twitterTest2")
	String twitterTest2(String ids) {
		if(ids == null) {
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
		if(user == null) {
			return "Specify a user, dummy!";
		}
		try {
			// This parses the json of the tweet results 
			String ret = "tweets:\n";
			JSONObject jsonobj = new JSONObject(Twitter.getLatest(user));
			JSONArray tweetlist = jsonobj.getJSONArray("data");
			for(int i = 0; i < tweetlist.length(); i++) {
				System.out.println(tweetlist.get(i));
				ret += "tweet " + i + ": " + tweetlist.get(i) + "\n";
			}
			for(String key : jsonobj.keySet()) {
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


	@GetMapping("/databaseTest")
	String databaseTest() {
		return DatabaseTest.getAllData();
	}

	@GetMapping("/databaseTest2")
	String databaseTest2(String number) {
		return DatabaseTest.putSomeData(number);
	}
}
