package com.keith.mydemo;

import java.io.*;
import java.net.*;
import java.util.*;

import org.apache.http.*;
import org.apache.http.HttpEntity;
import org.apache.http.client.*;
import org.apache.http.client.config.*;
import org.apache.http.client.methods.*;
import org.apache.http.client.utils.*;
import org.apache.http.impl.client.*;
import org.apache.http.message.*;
import org.apache.http.util.*;

public class Twitter {

	// To set your enviornment variables in your terminal run the following line:
	// export 'BEARER_TOKEN'='<your_bearer_token>'

	public static String showUser(String username) throws IOException, URISyntaxException {
		String bearerToken = System.getenv("BEARER_TOKEN");
		System.out.println(bearerToken);
		if (null != bearerToken) {
			// Replace comma separated usernames with usernames of your choice
			String response = getUsers(username, bearerToken);
			System.out.println(response);
			return response;
		}
		return "There was a problem getting you bearer token. Please make sure you set the BEARER_TOKEN environment variable";
	}

	public static String showSearch(String searchString) throws IOException, URISyntaxException {
		String bearerToken = System.getenv("BEARER_TOKEN");
		System.out.println(bearerToken);
		if (null != bearerToken) {
			// Replace comma separated usernames with usernames of your choice
			String response = search(searchString, bearerToken);
			System.out.println(response);
			return response;
		}
		return "There was a problem getting you bearer token. Please make sure you set the BEARER_TOKEN environment variable";
	}

	/*
	 * This method calls the v2 Users endpoint with usernames as query parameter
	 */
	private static String getUsers(String usernames, String bearerToken) throws IOException, URISyntaxException {
		String userResponse = null;

		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/users/by");
		ArrayList<NameValuePair> queryParameters;
		queryParameters = new ArrayList<>();
		queryParameters.add(new BasicNameValuePair("usernames", usernames));
		queryParameters.add(new BasicNameValuePair("user.fields", "created_at,description,pinned_tweet_id"));
		uriBuilder.addParameters(queryParameters);

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			userResponse = EntityUtils.toString(entity, "UTF-8");
		}
		return userResponse;
	}

	private static String search(String searchString, String bearerToken) throws IOException, URISyntaxException {
		String searchResponse = null;

		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/recent");
		ArrayList<NameValuePair> queryParameters;
		queryParameters = new ArrayList<>();
		queryParameters.add(new BasicNameValuePair("query", searchString));
		uriBuilder.addParameters(queryParameters);

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
		httpGet.setHeader("Content-Type", "application/json");

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			searchResponse = EntityUtils.toString(entity, "UTF-8");
		}
		return searchResponse;
	}
}
