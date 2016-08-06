package com.codepath.apps.tweettweet;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.codepath.apps.tweettweet.models.Tweet;
import com.squareup.picasso.Picasso;

import java.util.List;

/**
 * Created by melissahuang on 8/4/16.
 */
public class TweetsAdapter extends RecyclerView.Adapter<TweetsAdapter.ViewHolder> {

    public static class ViewHolder extends RecyclerView.ViewHolder {
        public ImageView ivProfileImage;
        public TextView tvUsername;
        public TextView tvTimestamp;
        public TextView tvBody;

        public ViewHolder(View itemView) {
            super(itemView);

            ivProfileImage = (ImageView) itemView.findViewById(R.id.ivProfileImage);
            tvUsername = (TextView) itemView.findViewById(R.id.tvUsername);
            tvTimestamp = (TextView) itemView.findViewById(R.id.tvTimestamp);
            tvBody = (TextView) itemView.findViewById(R.id.tvBody);
        }

    }

    private List<Tweet> mTweets;
    private Context mContext;

    public Context getmContext() {
        return mContext;
    }

    public TweetsAdapter(Context context, List<Tweet> tweets) {
        mTweets = tweets;
        mContext = context;
    }

    @Override
    public TweetsAdapter.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        Context context = parent.getContext();
        LayoutInflater layoutInflater = LayoutInflater.from(context);

        View tweetView = layoutInflater.inflate(R.layout.item_tweet, parent, false);

        ViewHolder viewHolder = new ViewHolder(tweetView);
        return viewHolder;
    }

    @Override
    public void onBindViewHolder(TweetsAdapter.ViewHolder viewHolder, int position) {
        Tweet tweet = mTweets.get(position);

        viewHolder.tvUsername.setText(tweet.getUser().getScreenName());
        viewHolder.tvTimestamp.setText(Tweet.getRelativeTimeAgo(tweet.getCreatedAt()));
        viewHolder.tvBody.setText(tweet.getBody());
        viewHolder.ivProfileImage.setImageResource(android.R.color.transparent);

        Picasso.with(mContext).load(tweet.getUser().getProfileImageUrl()).into(viewHolder.ivProfileImage);
    }

    @Override
    public int getItemCount() {
        return mTweets.size();
    }
}
