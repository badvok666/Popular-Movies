package com.example.badvok.popularmovies.RecyclerView;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.example.badvok.popularmovies.FetchFilms.FilmsItem;
import com.example.badvok.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.ArrayList;

/**
 * Created by badvok on 28-Nov-15.
 */
public class FilmRecyclerViewAdapter extends RecyclerView.Adapter<FilmRecyclerViewAdapter.FilmViewHolder> {

    ArrayList<FilmsItem> films;

    public FilmRecyclerViewAdapter(ArrayList<FilmsItem> films) {
        this.films = films;
    }

    @Override
    public void onAttachedToRecyclerView(RecyclerView recyclerView) {
        super.onAttachedToRecyclerView(recyclerView);
    }


    @Override
    public int getItemCount() {
        return films.size();
    }


    @Override
    public void onBindViewHolder(FilmViewHolder holder, int position) {
        Picasso.with(holder.itemView.getContext()).load("http://image.tmdb.org/t/p/w500//" + films.get(position).getPoster_path()).into(holder.imageView);
    }


    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        FilmViewHolder fvh = new FilmViewHolder(v);
        return fvh;
    }


    public static class FilmViewHolder extends RecyclerView.ViewHolder {

        CardView cv;
        ImageView imageView;

        public FilmViewHolder(View itemView) {
            super(itemView);
            cv = (CardView) itemView.findViewById(R.id.cv);
            imageView = (ImageView) itemView.findViewById(R.id.card_img_view);

        }
    }
}
