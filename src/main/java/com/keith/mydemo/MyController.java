package com.keith.mydemo;

import java.io.IOException;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.io.*;
import java.net.*;
import java.sql.SQLException;
import java.util.*;

import org.json.JSONArray;
import org.json.JSONObject;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

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
		double worldAverage = 0;
		Threat threat = new Threat();

		boolean exists = Twitter.doesAccountExist(username);
		ArrayList<String> suspiciousTweets = new ArrayList<String>();
		if (exists) {
			threatLevel = 1;
			threat.setMessage("All good");
			String result = getLatestTweet(username);
			threat.setLatestTweet(result);
			for (String tweet : Twitter.getLatestTweets(username)) {
				String date = tweet.substring(tweet.indexOf("{\"created_at\":\"") + "{\"created_at\":\"".length(),
						tweet.indexOf(".000Z\",\"id\""));
				String message = tweet.substring(tweet.indexOf("\"text\":") + "\"text\":".length(),
						tweet.indexOf("}]}"));
				if (tweet.toLowerCase().contains("tall")) {
					suspiciousTweets.add(message + " - Your height was found");
					threatLevel++;
				}
				if (tweet.toLowerCase().contains("name")) {
					suspiciousTweets.add(message + " - Your name was found");
					threatLevel++;
				}
				if (tweet.toLowerCase().contains("birthday")) {

					suspiciousTweets.add(message + " - Birthday might be: " + date);
					threatLevel++;
				}
				if (tweet.toLowerCase().contains("live")) {
					suspiciousTweets.add(message + " - Your location was found");
					threatLevel++;
				}
			}
		} else {
			threat.setMessage("Account does not exist!");
			threatLevel = 0;
		}
		threat.setUsername(username);
		threat.setThreatLevel(threatLevel);

		if (suspiciousTweets.size() > 0) {
			for (int i = 0; i < suspiciousTweets.size(); i++) {
				threat.setMessage("Suspicous Tweet: " + suspiciousTweets.get(i));
			}
		}
		if (DatabaseTest.isConnected()) {
			try {
				worldAverage = DatabaseTest.getWorldAverage();
			} catch (SQLException e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
			}
			threat.setWorldAverage(worldAverage);
			System.out.println("world average: " + worldAverage);
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
			ArrayList<String> tweets = Twitter.getLatestTweets(user);
			return tweets.get(0);
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
		try {
			return DatabaseTest.getAllData();
		} catch (SQLException e) {
			// TODO Auto-generated catch block
			e.printStackTrace();
		}
		return null;
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
