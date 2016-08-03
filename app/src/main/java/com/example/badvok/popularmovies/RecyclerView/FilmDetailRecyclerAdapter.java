package com.example.badvok.popularmovies.RecyclerView;

import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.badvok.popularmovies.AppDelegate;
import com.example.badvok.popularmovies.DataBase.Film;
import com.example.badvok.popularmovies.DataBase.Review;
import com.example.badvok.popularmovies.DataBase.Trailer;
import com.example.badvok.popularmovies.R;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;

/**
 * Created by simon on 31-May-16.
 */
public class FilmDetailRecyclerAdapter extends RecyclerView.Adapter<RecyclerView.ViewHolder> {

    private List<Trailer> trailers;
    private List<Review> reviews;
    private Film film;

    private static final int TYPE_HEADER = 0;
    private static final int TYPE_ITEM_REVIEW = 1;
    private static final int TYPE_ITEM_TRAILER = 2;

    RecyclerViewInterface mRecyclerViewInterface;

    private boolean toggleShowReviews = false;

    public void setRecyclerViewInterface (){

    }

    //todo try alternate constructor
    public FilmDetailRecyclerAdapter(List<Trailer> trailers, List<Review> reviews, Film film, RecyclerViewInterface recyclerViewInterface) {
        this.film = film;
        this.trailers = trailers;
        this.reviews = reviews;
        this.mRecyclerViewInterface = recyclerViewInterface;

        for(Trailer a : trailers){
            Log.d("trailers" , a.getName()+"");
        }
        for(Review a : reviews){
            Log.d("review8" , a.getAuthor()+"");
            Log.d("review8" , a.getId()+"");
        }

        if(reviews.size()==0){
            Log.d("testing2", "0");
        }

        if(reviews == null){
            Log.d("testing2", "null");
        }
    }

    @Override
    public int getItemCount() {
        if (trailers.size() == 0){
            return 1;
        }else{
            Log.d("testing2", trailers.size()+"");
            return trailers.size()+1;
        }

    }

    @Override
    public void onBindViewHolder(RecyclerView.ViewHolder holder, int position) {
        setUpClickListeners(holder);
        if (holder instanceof TrailerHolderItem) {
            Log.d("possss", "position: " + position);
            if(trailers.size() != 0){
                ((TrailerHolderItem)holder).image.setImageResource(R.mipmap.ic_launcher);
                ((TrailerHolderItem)holder).name.setText(trailers.get(position-1).getName());
            }

        }else if(holder instanceof ReviewHolderItem){
            if(reviews.size() != 0){
                ((ReviewHolderItem)holder).author.setText(reviews.get(0).getAuthor());
                ((ReviewHolderItem)holder).content.setText(reviews.get(position-1).getContent());
                ((ReviewHolderItem)holder).url.setText(reviews.get(position-1).getUrl());
            }
        }else if(holder instanceof HolderHeader){

            Log.d("possss", "position: " + position);
            ((HolderHeader)holder).title.setText(film.getTitle());
            ((HolderHeader)holder).rating.setText(film.getVote_average() + "/10");
            ((HolderHeader)holder).releaseDate.setText(film.getRelease_date());
            ((HolderHeader)holder).description.setText(film.getOverview());
            ((HolderHeader)holder).favorite.setText(film.isFavorite()? "Remove from favorites":"Add to favorites");
         //   ((TrailerHolderHeader)holder).title.setText(film.getTitle());
            Picasso.with(AppDelegate.ctx).load("http://image.tmdb.org/t/p/w500//" + film.getPoster_path()).into(((HolderHeader)holder).poster);

        }
    }

    public void setUpClickListeners(RecyclerView.ViewHolder holder){
        final RecyclerView.ViewHolder holderruu = holder;
        if(holder instanceof HolderHeader){

            if(reviews.size() == 0){
                ((HolderHeader)holder).toggle.setVisibility(View.GONE);
            }else{
                ((HolderHeader)holder).toggle.setVisibility(View.VISIBLE);
                }

            ((HolderHeader)holder).toggle.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {

                    if(mRecyclerViewInterface != null){
                        if(toggleShowReviews){
                            toggleShowReviews = false;
                        }else{
                            toggleShowReviews = true;
                        }
                        mRecyclerViewInterface.toggleRecyclerView(toggleShowReviews);
                    }
                }
            });

            ((HolderHeader)holder).favorite.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {


                    if(film.isFavorite()){
                        Film.updateFavorite(film.getId(),false);
                    }else{
                        Film.updateFavorite(film.getId(),true);
                    }

                    ((HolderHeader)holderruu).favorite.setText(film.isFavorite()? "Remove from favorites":"Add to favorites");

                }
            });
        }

    }


    @Override
    public RecyclerView.ViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v;
    //    TrailerHolder trailerHolder = new TrailerHolder(v);
        if(viewType == TYPE_ITEM_TRAILER){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.trailer_row, parent, false);
            Log.d("header", "normal");
            return new TrailerHolderItem(v);
        }else if(viewType == TYPE_ITEM_REVIEW){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.reivew_row, parent, false);
            return new ReviewHolderItem(v);
        }else if(viewType ==TYPE_HEADER){
            v = LayoutInflater.from(parent.getContext()).inflate(R.layout.header_row, parent, false);
            Log.d("header", "header");
            return new HolderHeader(v);
        }
        throw new RuntimeException("there is no type that matches the type " + viewType + " + make sure your using types correctly");
    }

    @Override
    public int getItemViewType(int position) {
        if(isPositionHeader(position)){
            return TYPE_HEADER;
        }else if(toggleShowReviews){
            return TYPE_ITEM_REVIEW;
        }else{
            return TYPE_ITEM_TRAILER;
        }

    }

    private boolean isPositionHeader(int position) {
        return position == 0;
    }


    public static class HolderHeader extends RecyclerView.ViewHolder{

        // ImageButton image;
        TextView title,rating,releaseDate,description;
        ImageView poster;
        Button toggle, favorite;

        public HolderHeader(View itemView) {
            super(itemView);
            //   image = (ImageButton)itemView.findViewById(R.id.image);
            //    name = (TextView)itemView.findViewById(R.id.title_tv);

            title = (TextView) itemView.findViewById(R.id.title);
            rating = (TextView) itemView.findViewById(R.id.rating);
            releaseDate = (TextView) itemView.findViewById(R.id.release_date);
            description = (TextView) itemView.findViewById(R.id.description);
            poster = (ImageView) itemView.findViewById(R.id.poster);
            toggle = (Button)itemView.findViewById(R.id.toggle);
            favorite = (Button)itemView.findViewById(R.id.favorite);


        }
    }

    public static class TrailerHolderItem extends RecyclerView.ViewHolder{

        ImageButton image;
        TextView name;

        public TrailerHolderItem(View itemView) {
            super(itemView);

            image = (ImageButton)itemView.findViewById(R.id.image);
            name = (TextView)itemView.findViewById(R.id.title_tv);

        }
    }

    public static class ReviewHolderItem extends RecyclerView.ViewHolder{

        // ImageButton image;
        TextView author,content,url;

        public ReviewHolderItem(View itemView) {
            super(itemView);
            //   image = (ImageButton)itemView.findViewById(R.id.image);
            //    name = (TextView)itemView.findViewById(R.id.title_tv);

            author = (TextView) itemView.findViewById(R.id.author_tv);
            content = (TextView) itemView.findViewById(R.id.content_tv);
            url = (TextView) itemView.findViewById(R.id.url_tv);

        }
    }
}
