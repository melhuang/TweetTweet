package com.codepath.apps.tweettweet;

import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;

import com.codepath.apps.tweettweet.fragments.TweetsListFragment;
import com.codepath.apps.tweettweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

public class TimelineActivity extends AppCompatActivity implements ComposeDialogFragment.ComposeDialogFragmentListener {

    private TwitterClient client;
    private TweetsListFragment fragmentTweetsList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_timeline);

        client = TwitterApplication.getRestClient();
        // if savedInstanceState != null, the fragment is already in memory
        if (savedInstanceState == null) {
            fragmentTweetsList = (TweetsListFragment) getSupportFragmentManager().findFragmentById(R.id.fragment_timeline);
        }
        populateTimeline();
    }

/* Menu replaced by FAB */

//    @Override
//    public boolean onCreateOptionsMenu(Menu menu) {
//        MenuInflater inflater = getMenuInflater();
//        inflater.inflate(R.menu.menu_timeline, menu);
//        return super.onCreateOptionsMenu(menu);
//    }

    private void populateTimeline() {
        long fetchMoreStart = -1;
//        long fetchMoreStart = tweets.size() > 0 ? tweets.get(tweets.size() - 1).getUid() : -1;
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // on success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                fragmentTweetsList.addAll(Tweet.fromJSONArray(json));
            }

            // on failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, fetchMoreStart);
    }

    public void presentCompose(View v) {
        FragmentManager fm = getSupportFragmentManager();
        ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance();
        composeDialogFragment.show(fm, "fragment_compose");
    }

    // ComposeDialogFragmentListener
    @Override
    public void onSaveTweet(String body) {
//        client.composeTweet(new JsonHttpResponseHandler() {
//            @Override
//            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
//                // fetch most recent tweets
//                Tweet newTweet = Tweet.fromJSON(json);
//                tweets.add(0, newTweet);
//                aTweets.notifyItemInserted(0);
//            }
//
//            // on failure
//            @Override
//            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
//                Log.d("DEBUG", errorResponse.toString());
//            }
//        }, body);
    }
}
