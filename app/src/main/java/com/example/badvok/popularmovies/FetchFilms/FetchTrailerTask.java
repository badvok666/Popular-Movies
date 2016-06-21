package com.example.badvok.popularmovies.FetchFilms;

import android.net.Uri;
import android.os.AsyncTask;
import android.util.Log;

import com.example.badvok.popularmovies.AppDelegate;
import com.example.badvok.popularmovies.DataBase.Trailer;
import com.example.badvok.popularmovies.FetchFilms.Interfaces.FetchTrailerListener;

import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;

import io.realm.Realm;

/**
 * Created by simon on 11-May-16.
 */
public class FetchTrailerTask extends AsyncTask<String, Void, Void>{

    String trailersJsonStr;
    FetchTrailerListener listener;

    public void setFetchTrailerListener(FetchTrailerListener listener){
        this.listener = listener;
    }

    @Override
    protected void onPreExecute() {

        super.onPreExecute();
    }

    @Override
    protected void onPostExecute(Void aVoid) {
        if (listener != null) {

            listener.onComplete();
        }
        super.onPostExecute(aVoid);
    }

    @Override
    protected void onProgressUpdate(Void... values) {
        super.onProgressUpdate(values);
    }

    @Override
    protected Void doInBackground(String... params) {

        HttpURLConnection urlConnection = null;
        BufferedReader reader = null;

        ApiKey apiKey = new ApiKey();
        String key = apiKey.getKey();

        final String BASE_URL = "http://api.themoviedb.org/3/movie/";
        final String SORT_BY = "sort_by";
        final String VIDEOS = "videos";
        final String API_KEY = "api_key";
        final String MOVIE_ID = params[1];

        try{

            Uri builtUri = Uri.parse(BASE_URL).buildUpon()
                    .appendPath(MOVIE_ID)
                    .appendPath(VIDEOS)
                    .appendQueryParameter(SORT_BY, params[0])
                    .appendQueryParameter(API_KEY, key)
                    .build();

            URL url = new URL(builtUri.toString());
            Log.d("url", url + "");

            urlConnection = (HttpURLConnection) url.openConnection();
            urlConnection.setRequestMethod("GET");
            urlConnection.connect();

            InputStream inputStrea = urlConnection.getInputStream();
            StringBuffer buffer = new StringBuffer();

            if(inputStrea == null){
                return null;
            }

            reader= new BufferedReader(new InputStreamReader(inputStrea));
            String line;
            while((line = reader.readLine()) != null){
                buffer.append(line + "\n");
            }

            if(buffer.length() == 0){
                return null;
            }

            trailersJsonStr = buffer.toString();

        }catch (IOException e){
            Log.e("FetchReviewTask", "Error ", e);
        }finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException e) {
                    Log.e("FetchReviewTask", "Error closing stream ", e);
                }
            }
        } try {
            createObject(trailersJsonStr, params[1]);
        } catch (JSONException e) {
            Log.e("JsonError", e.getMessage(), e);
            e.printStackTrace();
        }


        return null;
    }

    private void createObject(String json, String filmId)throws JSONException{

        String RESULTS = "results";
        String ID = "id";
        String ISO_639_1 = "iso_639_1";
        String ISO_3166_1 = "iso_3166_1";
        String KEY = "key";
        String NAME = "name";
        String SITE = "site";
        String SIZE = "size";
        String TYPE = "type";

        JSONObject trailerListJson = new JSONObject(json);
        JSONArray resultsArray = trailerListJson.getJSONArray(RESULTS);

        for (int i = 0; i < resultsArray.length(); i++){

            JSONObject trailerJSON = resultsArray.getJSONObject(i);
Log.d("testing", filmId);
            Trailer trailer = new Trailer(
                    trailerJSON.getString(ID),
                    filmId,
                    trailerJSON.getString(ISO_639_1),
                    trailerJSON.getString(ISO_3166_1),
                    trailerJSON.getString(KEY),
                    trailerJSON.getString(NAME),
                    trailerJSON.getString(SITE),
                    trailerJSON.getInt(SIZE),
                    trailerJSON.getString(TYPE)

            );
            Trailer.commitNewTrailer(trailer);
       /*     Realm realm = AppDelegate.getRealmInstance();
            try{
                realm.beginTransaction();
                realm.copyToRealm(trailer);
                realm.commitTransaction();

            } catch (Exception e) {
                Log.e("RealmError", "error" + e);
                realm.cancelTransaction();
            }*/

        }


    }
}
