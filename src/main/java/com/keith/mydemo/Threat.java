package com.keith.mydemo;

import java.util.HashMap;

public class Threat {
	String username;
	String latestTweet;
	int threatLevel;
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
}
