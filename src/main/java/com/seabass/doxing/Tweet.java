package com.seabass.doxing;

public class Tweet {
	public final String text;
	public final String id;
	public final String date;

	public Tweet(String text, String id, String date) {
		this.text = text;
		this.id = id;
		this.date = date;
	}

	@Override
	public String toString() {
		return "id:" + id + ", date:" + date + ", text:" + text;
	}
}
