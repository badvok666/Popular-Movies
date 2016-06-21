package com.example.badvok.popularmovies.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.TextView;

import com.example.badvok.popularmovies.DataBase.Trailer;
import com.example.badvok.popularmovies.R;

import java.util.List;

/**
 * Created by simon on 31-May-16.
 */
public class TrailerRecyclerAdapter extends RecyclerView.Adapter<TrailerRecyclerAdapter.TrailerHolder> {

    private List<Trailer> trailers;

    public TrailerRecyclerAdapter(List<Trailer> trailers) {
        this.trailers = trailers;
    }

    @Override
    public int getItemCount() {
        return trailers.size();
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position) {
        holder.image.setImageResource(R.mipmap.ic_launcher);
        holder.name.setText(trailers.get(position).getName());
    }

    @Override
    public TrailerHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_row, parent, false);
        TrailerHolder trailerHolder = new TrailerHolder(v);
        return trailerHolder;
    }

    @Override
    public void onBindViewHolder(TrailerHolder holder, int position, List<Object> payloads) {

        super.onBindViewHolder(holder, position, payloads);
    }

    public static class TrailerHolder extends RecyclerView.ViewHolder{

        ImageButton image;
        TextView name;

        public TrailerHolder(View itemView) {
            super(itemView);
            image = (ImageButton)itemView.findViewById(R.id.image);
            name = (TextView)itemView.findViewById(R.id.title_tv);

        }
    }
}
