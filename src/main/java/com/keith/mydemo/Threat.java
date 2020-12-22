package com.keith.mydemo;

import java.util.HashMap;

public class Threat {

	String username;
	String latestTweet;
	int threatLevel;
	String message;

	
	HashMap<String, String> data = new HashMap<String, String>();

	public Threat(String platform, String username) {
		data.put(platform, username);
	}

	public Threat(int favoriteNumber) {

	}

	int threatLvl = 2;

	public HashMap<String, String> getData() {
		return data;
	}

	public void setData(HashMap<String, String> data) {
		this.data = data;
	}

	public int getThreatLvl() {
		return threatLvl;
	}

	public void setThreatLvl(int threatLvl) {
		this.threatLvl = threatLvl;
	}

	public String getUsername() {
		return username;

	}

	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}

	
}
