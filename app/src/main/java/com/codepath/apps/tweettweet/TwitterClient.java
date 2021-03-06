package com.codepath.apps.tweettweet;

import android.content.Context;

import com.codepath.oauth.OAuthBaseClient;
import com.loopj.android.http.AsyncHttpResponseHandler;
import com.loopj.android.http.RequestParams;

import org.scribe.builder.api.Api;
import org.scribe.builder.api.TwitterApi;

/*
 * 
 * This is the object responsible for communicating with a REST API. 
 * Specify the constants below to change the API being communicated with.
 * See a full list of supported API classes: 
 *   https://github.com/fernandezpablo85/scribe-java/tree/master/src/main/java/org/scribe/builder/api
 * Key and Secret are provided by the developer site for the given API i.e dev.twitter.com
 * Add methods for each relevant endpoint in the API.
 * 
 * NOTE: You may want to rename this object based on the service i.e TwitterClient or FlickrClient
 * 
 */
public class TwitterClient extends OAuthBaseClient {
	public static final Class<? extends Api> REST_API_CLASS = TwitterApi.class;
	public static final String REST_URL = "https://api.twitter.com/1.1";
	public static final String REST_CONSUMER_KEY = "4H8JmetA5i1vGYtrpozZMyGxP";
	public static final String REST_CONSUMER_SECRET = "yNKO6A1mH6fdzAFNfXxaLk5hGhqyPEHK0IRB1bOMs97JPlrXr0";
	public static final String REST_CALLBACK_URL = "oauth://tweettweet"; // Change this (here and in manifest)

	public TwitterClient(Context context) {
		super(context, REST_API_CLASS, REST_URL, REST_CONSUMER_KEY, REST_CONSUMER_SECRET, REST_CALLBACK_URL);
	}

	// GET home timeline
	public void getHomeTimeline(AsyncHttpResponseHandler handler, long id) {
		String apiUrl = getApiUrl("statuses/home_timeline.json");
		// Can specify query string params directly or through RequestParams.
		RequestParams params = new RequestParams();
		params.put("count", "25");
        if (id != -1) {
            params.put("max_id", id);
        } else {
            params.put("since_id", 1);
        }

		client.get(apiUrl, params, handler);
	}

    // GET mentions timeline
    public void getMentionsTimeline(AsyncHttpResponseHandler handler, long id) {
        String apiUrl = getApiUrl("statuses/mentions_timeline.json");
        // Can specify query string params directly or through RequestParams.
        RequestParams params = new RequestParams();
        params.put("count", "25");
        if (id != -1) {
            params.put("max_id", id);
        } else {
            params.put("since_id", 1);
        }

        client.get(apiUrl, params, handler);
    }



    // GET user timeline
    public void getUserTimeline(AsyncHttpResponseHandler handler, long id, String screenName) {
        String apiUrl = getApiUrl("statuses/user_timeline.json");

        RequestParams params = new RequestParams();
        params.put("count", "25");
        if (id != -1) {
            params.put("max_id", id);
        } else {
            params.put("since_id", 1);
        }

        if (screenName != null) {
            params.put("screen_name", screenName);
        }
        params.put("screen_name", screenName);
        client.get(apiUrl, params, handler);
    }

    // GET user info
    public void getUserInfo(AsyncHttpResponseHandler handler, String screenName) {
        String apiUrl = getApiUrl("users/show.json");

        RequestParams params = new RequestParams();
        params.put("screen_name", screenName);

        client.get(apiUrl, params, handler);
    }

	// POST new tweet
    public void composeTweet(AsyncHttpResponseHandler handler, String body) {
        String apiUrl = getApiUrl("statuses/update.json");
        RequestParams params = new RequestParams();
        params.put("status", body);
        client.post(apiUrl ,params, handler);
    }


	/* 1. Define the endpoint URL with getApiUrl and pass a relative path to the endpoint
	 * 	  i.e getApiUrl("statuses/home_timeline.json");
	 * 2. Define the parameters to pass to the request (query or body)
	 *    i.e RequestParams params = new RequestParams("foo", "bar");
	 * 3. Define the request method and make a call to the client
	 *    i.e client.get(apiUrl, params, handler);
	 *    i.e client.post(apiUrl, params, handler);
	 */
}