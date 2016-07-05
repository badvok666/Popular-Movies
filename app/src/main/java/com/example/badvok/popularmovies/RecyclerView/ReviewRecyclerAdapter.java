package com.example.badvok.popularmovies.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
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
public class ReviewRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    List<Review> reviews;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM = 1;


    public ReviewRecyclerAdapter(List<Review> reviews) {
        this.reviews = reviews;

    }


    @Override
    public int getItemCount() {
        return reviews.size();
    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {

        if(holder instanceof ReviewHolderItem ){
            ((ReviewHolderItem) holder).author.setText(reviews.get(position).getAuthor());
            ((ReviewHolderItem) holder).content.setText(reviews.get(position).getContent());
            ((ReviewHolderItem) holder).url.setText(reviews.get(position).getUrl());
        }else if(holder instanceof ReviewHolderHeader){
            ((ReviewHolderHeader) holder).author.setText("BLUBA BLEE BA BUGHA ");
            ((ReviewHolderHeader) holder).content.setText("Argh here be header");
            ((ReviewHolderHeader) holder).url.setText(reviews.get(position).getUrl());
        }


    }

    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
        if(viewType == TYPE_ITEM){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reivew_row, parent, false);
            Log.d("header", "normal");
            return new ReviewHolderItem(v);
        }else if(viewType ==TYPE_HEADER){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reivew_row, parent, false);
            Log.d("header", "header");
            return new ReviewHolderHeader(v);
        }

        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");

    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position)){
            return TYPE_HEADER;
        }
        return TYPE_ITEM;
    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }

    public static class ReviewHolderItem extends RecyclerView.ViewHolder{

        TextView author;
        TextView content;
        TextView url;


        public ReviewHolderItem(View itemView) {
            super(itemView);

            author = (TextView)itemView.findViewById(R.id.author_tv);
            content = (TextView)itemView.findViewById(R.id.content_tv);
            url = (TextView)itemView.findViewById(R.id.url_tv);


        }
    }

    public static class ReviewHolderHeader extends RecyclerView.ViewHolder{

        TextView author;
        TextView content;
        TextView url;


        public ReviewHolderHeader(View itemView) {
            super(itemView);

            author = (TextView)itemView.findViewById(R.id.author_tv);
            content = (TextView)itemView.findViewById(R.id.content_tv);
            url = (TextView)itemView.findViewById(R.id.url_tv);


        }
    }

}
