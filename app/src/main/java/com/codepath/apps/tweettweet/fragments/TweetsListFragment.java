package com.codepath.apps.tweettweet.fragments;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.codepath.apps.tweettweet.EndlessRecyclerViewScrollListener;
import com.codepath.apps.tweettweet.R;
import com.codepath.apps.tweettweet.TweetsAdapter;
import com.codepath.apps.tweettweet.models.Tweet;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by melissahuang on 8/6/16.
 */
public class TweetsListFragment extends Fragment {

    protected TweetsAdapter aTweets;
    protected ArrayList<Tweet> tweets;
    protected RecyclerView rvTweets;

    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_tweets_list, container, false);
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

        return v;
    }

    public void populateTimeline() {
        // subclasses override
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        tweets = new ArrayList<>();
        aTweets = new TweetsAdapter(getActivity(), tweets);
        super.onCreate(savedInstanceState);
    }

    // Fragment accessor methods
    public void addAll(List<Tweet> tweets) {
        int start = aTweets.getItemCount();
        this.tweets.addAll(tweets);
        aTweets.notifyItemRangeInserted(start, aTweets.getItemCount() - 1);
    }

}
