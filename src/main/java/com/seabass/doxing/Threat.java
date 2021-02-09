package com.seabass.doxing;

import java.util.HashMap;

public class Threat {
	String username;
	String latestTweet;
	int threatLevel;
	String message;
	double worldAvg;
	public String getMessage() {
		return message;
	}
	public void setMessage(String message) {
		this.message = message;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getLatestTweet() {
		return latestTweet;
	}
	public void setLatestTweet(String latestTweet) {
		this.latestTweet = latestTweet;
	}
	public int getThreatLevel() {
		return threatLevel;
	}
	public void setThreatLevel(int threatLevel) {
		this.threatLevel = threatLevel;
	}
	public double getWorldAvg() {
		return worldAvg;
	}
	public void setWorldAverage(double worldAvg) {
		this.worldAvg = worldAvg;
	}
	
	
	
	
}
