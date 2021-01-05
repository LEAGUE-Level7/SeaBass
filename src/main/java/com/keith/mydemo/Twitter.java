package com.keith.mydemo;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStreamReader;
import java.net.URISyntaxException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.HttpClient;
import org.apache.http.client.config.CookieSpecs;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.utils.URIBuilder;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.json.JSONArray;
import org.json.JSONObject;

public class Twitter {

	// To set your enviornment variables in your terminal run the following line:
	// export 'BEARER_TOKEN'='<your_bearer_token>'

	public static void showFilteredStream() throws IOException, URISyntaxException {
		String bearerToken = System.getenv("BEARER_TOKEN");
		if (null != bearerToken) {
			Map<String, String> rules = new HashMap<>();
			rules.put("Keithslife has:images", "keith images");
			rules.put("Keithslife has:media", "keith media");
			rules.put("Keithslife has:mentions", "keith mention");
			setupRules(bearerToken, rules);
			connectStream(bearerToken);
		} else {
			System.out.println(
					"There was a problem getting you bearer token. Please make sure you set the BEARER_TOKEN environment variable");
		}

	}

	public static boolean doesAccountExist(String username) throws IOException, URISyntaxException {
		String bearerToken = System.getenv("BEARER_TOKEN");
		System.out.println(bearerToken);
		if (null != bearerToken) {
			// Replace comma separated usernames with usernames of your choice
			String response = getUsers(username, bearerToken);
			System.out.println(response);

			JSONObject jsonobj = new JSONObject(response);
			if (jsonobj.has("errors")) {
				System.out.println("There is an 'errors' field");
				return false;
			} else if (jsonobj.has("data")) {
				System.out.println("There is a 'data' field");
				return true;
			}

		}
		return false;
	}

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
	// export 'BEARER_TOKEN'='<your_bearer_token>'

	public static String doUser(String username) throws IOException, URISyntaxException {
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

	public static String dotweet(String ids) throws IOException, URISyntaxException {
		String bearerToken = System.getenv("BEARER_TOKEN");
		System.out.println(bearerToken);
		if (null != bearerToken) {
			// Replace comma separated usernames with usernames of your choice
			String response = getTweets(ids, bearerToken);
			System.out.println(response);
			return response;
		}
		return "There was a problem getting you bearer token. Please make sure you set the BEARER_TOKEN environment variable";
	}

	
	/***
	 * Returns and array of strings. Each string is a recent tweet from the user.
	 */
	public static ArrayList<String> getLatestTweets(String user) throws IOException, URISyntaxException {
		String bearerToken = System.getenv("BEARER_TOKEN");
		System.out.println(bearerToken);
		ArrayList<String> list = new ArrayList<>();
		if (null != bearerToken) {
			// Replace comma separated usernames with usernames of your choice
			String response = getLatestTweetsJSON(user, bearerToken);
			JSONObject jsonobj = new JSONObject(response);
			JSONArray jsonarr = jsonobj.getJSONArray("data");
			for(int i = 0; i < jsonarr.length(); i++) {
				JSONObject arrayelement = jsonarr.getJSONObject(i);
				System.out.println(arrayelement.getString("id"));
				System.out.println(arrayelement.getString("text"));
				list.add(arrayelement.getString("text"));
			}
		}
		return list;
	}

	private static String getTweets(String ids, String bearerToken) throws IOException, URISyntaxException {
		String userResponse = null;

		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets");
		ArrayList<NameValuePair> queryParameters;
		queryParameters = new ArrayList<>();
		queryParameters.add(new BasicNameValuePair("ids", ids));
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

	/**
	 * Gets latest tweets from account. Returns unparsed raw and wriggling json string
	 */
	private static String getLatestTweetsJSON(String user, String bearerToken) throws IOException, URISyntaxException {
		String userResponse = null;

		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/recent");
		ArrayList<NameValuePair> queryParameters;
		queryParameters = new ArrayList<>();
		queryParameters.add(new BasicNameValuePair("query", "from:" + user));
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

	/*
	 * This method calls the filtered stream endpoint and streams Tweets from it
	 */
	private static void connectStream(String bearerToken) throws IOException, URISyntaxException {

		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream");

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));

		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			BufferedReader reader = new BufferedReader(new InputStreamReader((entity.getContent())));
			String line = reader.readLine();
			while (line != null) {
				System.out.println(line);
				line = reader.readLine();
			}
		}

	}

	/*
	 * Helper method to setup rules before streaming data
	 */
	private static void setupRules(String bearerToken, Map<String, String> rules)
			throws IOException, URISyntaxException {
		List<String> existingRules = getRules(bearerToken);
		if (existingRules.size() > 0) {
			deleteRules(bearerToken, existingRules);
		}
		createRules(bearerToken, rules);
	}

	/*
	 * Helper method to create rules for filtering
	 */
	private static void createRules(String bearerToken, Map<String, String> rules)
			throws URISyntaxException, IOException {
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream/rules");

		HttpPost httpPost = new HttpPost(uriBuilder.build());
		httpPost.setHeader("Authorization", String.format("Bearer %s", bearerToken));
		httpPost.setHeader("content-type", "application/json");
		StringEntity body = new StringEntity(getFormattedString("{\"add\": [%s]}", rules));
		httpPost.setEntity(body);
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			System.out.println(EntityUtils.toString(entity, "UTF-8"));
		}
	}

	/*
	 * Helper method to get existing rules
	 */
	private static List<String> getRules(String bearerToken) throws URISyntaxException, IOException {
		List<String> rules = new ArrayList<>();
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream/rules");

		HttpGet httpGet = new HttpGet(uriBuilder.build());
		httpGet.setHeader("Authorization", String.format("Bearer %s", bearerToken));
		httpGet.setHeader("content-type", "application/json");
		HttpResponse response = httpClient.execute(httpGet);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			JSONObject json = new JSONObject(EntityUtils.toString(entity, "UTF-8"));
			if (json.length() > 1) {
				JSONArray array = (JSONArray) json.get("data");
				for (int i = 0; i < array.length(); i++) {
					JSONObject jsonObject = (JSONObject) array.get(i);
					rules.add(jsonObject.getString("id"));
				}
			}
		}
		return rules;
	}

	/*
	 * Helper method to delete rules
	 */
	private static void deleteRules(String bearerToken, List<String> existingRules)
			throws URISyntaxException, IOException {
		HttpClient httpClient = HttpClients.custom()
				.setDefaultRequestConfig(RequestConfig.custom().setCookieSpec(CookieSpecs.STANDARD).build()).build();

		URIBuilder uriBuilder = new URIBuilder("https://api.twitter.com/2/tweets/search/stream/rules");

		HttpPost httpPost = new HttpPost(uriBuilder.build());
		httpPost.setHeader("Authorization", String.format("Bearer %s", bearerToken));
		httpPost.setHeader("content-type", "application/json");
		StringEntity body = new StringEntity(getFormattedString("{ \"delete\": { \"ids\": [%s]}}", existingRules));
		httpPost.setEntity(body);
		HttpResponse response = httpClient.execute(httpPost);
		HttpEntity entity = response.getEntity();
		if (null != entity) {
			System.out.println(EntityUtils.toString(entity, "UTF-8"));
		}
	}

	private static String getFormattedString(String string, List<String> ids) {
		StringBuilder sb = new StringBuilder();
		if (ids.size() == 1) {
			return String.format(string, "\"" + ids.get(0) + "\"");
		} else {
			for (String id : ids) {
				sb.append("\"" + id + "\"" + ",");
			}
			String result = sb.toString();
			return String.format(string, result.substring(0, result.length() - 1));
		}
	}

	private static String getFormattedString(String string, Map<String, String> rules) {
		StringBuilder sb = new StringBuilder();
		if (rules.size() == 1) {
			String key = rules.keySet().iterator().next();
			return String.format(string, "{\"value\": \"" + key + "\", \"tag\": \"" + rules.get(key) + "\"}");
		} else {
			for (Map.Entry<String, String> entry : rules.entrySet()) {
				String value = entry.getKey();
				String tag = entry.getValue();
				sb.append("{\"value\": \"" + value + "\", \"tag\": \"" + tag + "\"}" + ",");
			}
			String result = sb.toString();
			return String.format(string, result.substring(0, result.length() - 1));
		}
	}

}
