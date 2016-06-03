package com.example.badvok.popularmovies.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.example.badvok.popularmovies.DataBase.Review;
import com.example.badvok.popularmovies.R;

import java.util.List;

/**
 * Created by simon on 26-May-16.
 */
public class ReviewRecyclerAdapter extends RecyclerView.Adapter<ReviewRecyclerAdapter.ReviewHolder> {

    List<Review> reviews;

    public ReviewRecyclerAdapter(List<Review> reviews) {
        this.reviews = reviews;

    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @Override
    public void onBindViewHolder(ReviewRecyclerAdapter.ReviewHolder holder, int position) {
            holder.author.setText(reviews.get(position).getAuthor());
            holder.content.setText(reviews.get(position).getContent());
            holder.url.setText(reviews.get(position).getUrl());
    }

    @Override
    public ReviewRecyclerAdapter.ReviewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reivew_row, parent, false);

        return  new ReviewHolder(v);

    }

    public static class ReviewHolder extends RecyclerView.ViewHolder{

        TextView author;
        TextView content;
        TextView url;


        public ReviewHolder(View itemView) {
            super(itemView);

            author = (TextView)itemView.findViewById(R.id.author_tv);
            content = (TextView)itemView.findViewById(R.id.content_tv);
            url = (TextView)itemView.findViewById(R.id.url_tv);


        }
    }

}
