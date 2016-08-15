package com.codepath.apps.tweettweet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.design.widget.FloatingActionButton;
import android.support.v4.app.FragmentManager;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.tweettweet.ComposeDialogFragment;
import com.codepath.apps.tweettweet.EndlessRecyclerViewScrollListener;
import com.codepath.apps.tweettweet.R;
import com.codepath.apps.tweettweet.TwitterApplication;
import com.codepath.apps.tweettweet.TwitterClient;
import com.codepath.apps.tweettweet.models.Tweet;
import com.loopj.android.http.JsonHttpResponseHandler;

import org.json.JSONArray;
import org.json.JSONObject;

import cz.msebera.android.httpclient.Header;

/**
 * Created by melissahuang on 8/9/16.
 */
public class HomeTimelineFragment extends TweetsListFragment implements ComposeDialogFragment.ComposeDialogFragmentListener {
    private TwitterClient client;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_home_timeline, container, false);
        rvTweets = (RecyclerView) v.findViewById(R.id.rvTweets);
        rvTweets.setAdapter(aTweets);

        LinearLayoutManager linearLayoutManager = new LinearLayoutManager(getActivity());
        rvTweets.setLayoutManager(linearLayoutManager);
        rvTweets.setHasFixedSize(true);
        rvTweets.addOnScrollListener(new EndlessRecyclerViewScrollListener(linearLayoutManager) {
            @Override
            public void onLoadMore(int page, int totalItemsCount) {
                populateTimeline();
            }
        });

        FloatingActionButton fab = (FloatingActionButton) v.findViewById(R.id.fabCompose);
        fab.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                presentCompose(view);
            }
        });

        return v;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        client = TwitterApplication.getRestClient();
        populateTimeline();
    }

    @Override
    public void populateTimeline() {
//        long fetchMoreStart = -1;
        long fetchMoreStart = tweets.size() > 0 ? tweets.get(tweets.size() - 1).getUid() : -1;
        client.getHomeTimeline(new JsonHttpResponseHandler() {
            // on success

            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONArray json) {
                addAll(Tweet.fromJSONArray(json));
            }

            // on failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, fetchMoreStart);
    }

    public void presentCompose(View v) {
        FragmentManager fm = getFragmentManager();
        ComposeDialogFragment composeDialogFragment = ComposeDialogFragment.newInstance();
        composeDialogFragment.setTargetFragment(this, 300);
        composeDialogFragment.show(fm, "fragment_compose");
    }

    // ComposeDialogFragmentListener
    @Override
    public void onSaveTweet(String body) {
        client.composeTweet(new JsonHttpResponseHandler() {
            @Override
            public void onSuccess(int statusCode, Header[] headers, JSONObject json) {
                // fetch most recent tweets
                Tweet newTweet = Tweet.fromJSON(json);
                tweets.add(0, newTweet);
                aTweets.notifyItemInserted(0);
            }

            // on failure
            @Override
            public void onFailure(int statusCode, Header[] headers, Throwable throwable, JSONObject errorResponse) {
                Log.d("DEBUG", errorResponse.toString());
            }
        }, body);
    }
}
