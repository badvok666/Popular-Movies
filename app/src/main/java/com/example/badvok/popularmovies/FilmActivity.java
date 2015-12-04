package com.example.badvok.popularmovies;

import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.app.AppCompatActivity;
import android.text.method.ScrollingMovementMethod;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.badvok.popularmovies.FetchFilms.FilmsItem;
import com.squareup.picasso.Picasso;

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
    public static class PlaceholderFragment extends Fragment {

        TextView title, rating, releaseDate, description;
        ImageView poster;

        public PlaceholderFragment() {
        }

        @Override
        public View onCreateView(LayoutInflater inflater, ViewGroup container,
                                 Bundle savedInstanceState) {

            View rootView = inflater.inflate(R.layout.fragment_film, container, false);
            title = (TextView) rootView.findViewById(R.id.title);
            rating = (TextView) rootView.findViewById(R.id.rating);
            releaseDate = (TextView) rootView.findViewById(R.id.release_date);
            description = (TextView) rootView.findViewById(R.id.description);
            poster = (ImageView) rootView.findViewById(R.id.poster);
            Intent intent = getActivity().getIntent();

            description.setMovementMethod(new ScrollingMovementMethod());

            if (intent != null && intent.hasExtra("com.example.badvok.pupularmovies.FilmsItem")) {

                Bundle b = intent.getExtras();
                FilmsItem film = b.getParcelable("com.example.badvok.pupularmovies.FilmsItem");
                title.setText(film.getTitle());
                rating.setText(film.getVote_average() + "/10");
                releaseDate.setText(film.getRelease_date());
                description.setText(film.getOverview());
                Picasso.with(getContext()).load("http://image.tmdb.org/t/p/w500//" + film.getPoster_path()).into(poster);


            }
            return rootView;
        }
    }

}
