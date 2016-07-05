package com.example.badvok.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.text.method.ScrollingMovementMethod;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.badvok.popularmovies.DataBase.Film;
import com.example.badvok.popularmovies.DataBase.Review;
import com.example.badvok.popularmovies.DataBase.Trailer;
import com.example.badvok.popularmovies.FetchFilms.FetchReviewTask;
import com.example.badvok.popularmovies.FetchFilms.FetchTrailerTask;
import com.example.badvok.popularmovies.FetchFilms.Interfaces.FetchReviewsListener;
import com.example.badvok.popularmovies.FetchFilms.Interfaces.FetchTrailerListener;
import com.example.badvok.popularmovies.RecyclerView.RecyclerViewInterface;
import com.example.badvok.popularmovies.RecyclerView.TrailerRecyclerAdapter;
import com.squareup.picasso.Picasso;

import java.util.List;

import io.realm.Realm;

/**
 * Created by badvok on 29-Nov-15.
 */
public class FilmActivity extends AppCompatActivity {



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.film_detail);
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .add(R.id.container, new PlaceholderFragment())
                    .commit();
        }

        if (getActionBar() != null) {
            getActionBar().setDisplayHomeAsUpEnabled(true);
        }


    }

    public void exchangeFrgment(){
        getSupportFragmentManager().beginTransaction()
                .add(R.id.container, new ReviewFragment())
                .commit();
    }


    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.detail, menu);
        return true;
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.
        int id = item.getItemId();

        //noinspection SimplifiableIfStatement
        if (id == R.id.action_settings) {
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    /**
     * A placeholder fragment containing fragment_film
     * a detail view for films
     */
    public static class PlaceholderFragment extends Fragment implements RecyclerViewInterface {

        TrailerRecyclerAdapter trailerRecyclerAdapter;


        List<Review> mReviews = null;
        List<Trailer> mTrailers = null;
        RecyclerView recyclerView;

        Boolean fetchedTrailers = false;
        Boolean fetchedReviews = false;
  //      TextView title, rating, releaseDate, description;
 //       ImageView poster;

        @Override
        public void toggleRecyclerView(boolean showReviews) {
            Log.d("testReview","dddd===" + showReviews);

            trailerRecyclerAdapter.notifyDataSetChanged();

        }

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_film, container, false);
         //   title = (TextView) rootView.findViewById(R.id.title);
         //   rating = (TextView) rootView.findViewById(R.id.rating);
         //   releaseDate = (TextView) rootView.findViewById(R.id.release_date);
       //     description = (TextView) rootView.findViewById(R.id.description);
       //     poster = (ImageView) rootView.findViewById(R.id.poster);
            Intent intent = getActivity().getIntent();



            recyclerView = (RecyclerView) rootView.findViewById(R.id.trailerRecyclerView);
            recyclerView.setLayoutManager(new GridLayoutManager(AppDelegate.ctx, 1));



            recyclerView.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View v) {
                    v.getParent().requestDisallowInterceptTouchEvent(true);

                }
            });

        //    description.setMovementMethod(new ScrollingMovementMethod());

            if (intent != null && intent.hasExtra("com.example.badvok.pupularmovies.Film")) {
              //  final Realm realm = AppDelegate.getRealmInstance();

               // Bundle b = intent.getExtras();
                final String filmId = intent.getStringExtra("com.example.badvok.pupularmovies.Film");

                FetchTrailerTask fetchTrailerTask = new FetchTrailerTask();
                fetchTrailerTask.execute(AppDelegate.ORDER_PERAM, filmId);
                fetchTrailerTask.setFetchTrailerListener(new FetchTrailerListener() {
                    @Override
                    public void onComplete() {

                        fetchedTrailers = true;
                        if(fetchedReviews){
                            setUpRecyclerView(filmId);
                        }

                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onProgress() {

                    }
                });

                FetchReviewTask fetchReviewTask = new FetchReviewTask();
                fetchReviewTask.execute(AppDelegate.ORDER_PERAM, filmId);
                fetchReviewTask.setFetchReviewsListener(new FetchReviewsListener() {
                    @Override
                    public void onComplete() {
                        fetchedReviews = true;
                        if(fetchedTrailers){
                            setUpRecyclerView(filmId);
                        }
                    }

                    @Override
                    public void onError() {

                    }

                    @Override
                    public void onProgress() {

                    }
                });



               // Realm realm = AppDelegate.getRealmInstance();
          //      Film film = realm.where(Film.class).equalTo("id",filmId).findFirst();

                //FilmsItem film = b.getParcelable("com.example.badvok.pupularmovies.FilmsItem");
            //    title.setText(film.getTitle());
            //    rating.setText(film.getVote_average() + "/10");
            //    releaseDate.setText(film.getRelease_date());
            //    description.setText(film.getOverview());
            //    Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500//" + film.getPoster_path()).into(poster);
            //    Log.d("apistuff", film.getId());

           //     mReviews = Review.getReviews(filmId);
           //     mTrailers = Trailer.getTrailers(filmId);
          //      showReviews();



            }
            return rootView;
        }

        public void setUpRecyclerView(String filmId){



            final Realm realm = AppDelegate.getRealmInstance();

            List<Review> reviewslist = realm.where(Review.class).findAll();

            for(Review r: reviewslist){
                Log.d("review8",r.getAuthor()+" " +r.getFilmId());
            }

            trailerRecyclerAdapter = new TrailerRecyclerAdapter(realm.where(Trailer.class).equalTo("filmId",filmId).findAll(),
                    realm.where(Review.class).equalTo("filmId",filmId).findAll(),
                    realm.where(Film.class).equalTo("id",filmId).findFirst(),
                    PlaceholderFragment.this);

            recyclerView.setAdapter(trailerRecyclerAdapter);

        }

        public void showReviews(){

            for (int i = 0; i < mReviews.size(); i++) {
                Log.d("reviews",mReviews.get(i).getAuthor());
            }


        }

    }

    public static class ReviewFragment extends Fragment {

    }




}
