package com.example.badvok.popularmovies;

import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.CardView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import java.util.List;

/**
 * Created by badvok on 28-Nov-15.
 */
public class FilmRecyclerViewAdapter extends RecyclerView.Adapter<FilmRecyclerViewAdapter.FilmViewHolder> {


    List<DummyData>films;

    FilmRecyclerViewAdapter(List<DummyData>films){
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
        holder.title.setText(films.get(position).filmName);
        holder.rating.setText(films.get(position).rating+"/10");
        holder.description.setText(films.get(position).description);
    }

    @Override
    public FilmViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {

        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.card_view, parent, false);
        FilmViewHolder fvh = new FilmViewHolder(v);
        return  fvh;
    }

    public static class FilmViewHolder extends RecyclerView.ViewHolder{

        CardView cv;
        TextView title,rating,description;



        public FilmViewHolder(View itemView) {
            super(itemView);
            cv = (CardView)itemView.findViewById(R.id.cv);
            title = (TextView)itemView.findViewById(R.id.title);
            rating = (TextView)itemView.findViewById(R.id.rating);
            description = (TextView)itemView.findViewById(R.id.description);
        }
    }
}
