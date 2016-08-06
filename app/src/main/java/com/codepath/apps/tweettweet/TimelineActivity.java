package com.codepath.apps.tweettweet;

import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;

import com.codepath.apps.tweettweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity {

    private TwitterClient client;
    private TweetsAdapter aTweets;
    private ArrayList<Tweet> tweets;
    private RecyclerView rvTweets;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);
        rvTweets = (RecyclerView) findViewById(R.id.rvTweets);
        tweets = new ArrayList<>();
        aTweets = new TweetsAdapter(this, tweets);
        rvTweets.setAdapter(aTweets);
        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(this);
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setHasFixedSize(true);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline();
            }
        });
        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    private void populateTimeline() {
        long fetchMoreStart = tweets.size() > 0 ? tweets.get(tweets.size() - 1).getUid() : -1;
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // on success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                int start = aTweets.getItemCount();
                tweets.addAll(Tweet.fromJSONArray(json));
                aTweets.notifyItemRangeInserted(start, aTweets.getItemCount() - 1);
            }

            // on failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, fetchMoreStart);
    }
}
