package com.keith.mydemo;

import java.io.*;
import java.net.*;
import java.util.Random;

import org.json.*;
import org.springframework.web.bind.annotation.*;

@RestController
public class MyController {

	public MyController() {
		DatabaseTest.initializeConnection();
	}

	@PostMapping("/getScore")
	Threat myMethod(@RequestBody String body) throws IOException, URISyntaxException {
		System.out.println("post request: " + body);
		JSONObject jsonobj = new JSONObject(body);
		String username = jsonobj.getString("username");

		boolean checked = jsonobj.getBoolean("collectdata");
		int threatLevel = 0;
		Threat threat = new Threat();

		boolean exists = Twitter.doesAccountExist(username);

		if (exists) {
			

			String result = getLatestTweet(username);
			threat.setLatestTweet(result);
			threat.setMessage("All good");
			threat.setUsername(username);
			threat.setThreatLevel(1);
		} else {
			threat.setMessage("Account does not exist!");
			threatLevel = 0;
		}

		if (DatabaseTest.isConnected()) {
			double worldAverage = DatabaseTest.getWorldAverage();
			// threat.setWorldAverage(worldAverage);
			if (checked) {
				DatabaseTest.putSomeData("" + threatLevel);
			}
		}
		return threat;
	}

	@GetMapping("/twitterUser")
	String twitterUser(String username) {
		if (username == null) {
			username = "12345szxcvu653hfdsy5643gfda";
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
		if (user == null) {
			user = "elonmusk";
		}
		try {
			// This parses the json of the tweet results
			JSONObject jsonobj = new JSONObject(Twitter.getLatest(user));
			String tweetlist = jsonobj.getJSONArray("data").getJSONObject(0).getString("text");
			return tweetlist;
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
