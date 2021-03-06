//gallery tutorial androidauthority how to build an image gallery app
package com.example.android.popularmoviesproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.app.ShareCompat;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ArrayAdapter;

import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesproject.utilities.MovieData;
import com.example.android.popularmoviesproject.utilities.NetworkUtil;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONException;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements MovieListAdapter.ListItemClickListener {

    private Toast mToast;
    private List<MovieData> dataPrepArray;
    private RecyclerView recyclerview;
    private MovieListAdapter mAdapter;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        MovieListAdapter mAdapter = new MovieListAdapter(MainActivity.this, dataPrepArray, this);
        //Recycle View
        RecyclerView recyclerView = (RecyclerView) findViewById(R.id.rv_galleryView);
        recyclerView.setHasFixedSize(true);
        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(), 3);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(mAdapter);

        new FetchMovieTask().execute(NetworkUtil.buildURL("mdbSortQuery"));
    }

    public class FetchMovieTask extends AsyncTask<URL, Void, String> {

        @Override
        protected String doInBackground(URL... params) {
            URL moviesURL = params[0];

            try {
                String movieResult = NetworkUtil.getResponseFromHttpUrl(moviesURL);
                return movieResult;

            } catch (IOException e) {
                e.printStackTrace();
                return null;
            }

        }

        @Override
        public void onPostExecute(String movieResult) {
            //JSON

            try {
                JSONObject jsonObject = new JSONObject(movieResult);
                JSONArray jArray = (JSONArray) jsonObject.getJSONArray("results");

                dataPrepArray = new ArrayList<>();
                for (int i = 0; i < jArray.length(); ++i) {
                    JSONObject movieObject = jArray.getJSONObject(i);
                    MovieData movieData = new MovieData();
                    movieData.moviePoster = movieObject.getString("poster_path");
                    movieData.movieAvgRating = movieObject.getDouble("vote_average");
                    movieData.movieTitle = movieObject.getString("title");
                    movieData.movieReleaseDate = movieObject.getString("release_date");
                    movieData.moviePlot = movieObject.getString("overview");
                    dataPrepArray.add(movieData);
                }//for loop

            } catch (JSONException e) {
                e.printStackTrace();
            }//catch

            mAdapter = new MovieListAdapter(MainActivity.this, dataPrepArray, MainActivity.this);;
        }//PostExecute

        }//AsyncTask


        //Actionbar Spinner
        @Override
        public boolean onCreateOptionsMenu(Menu menu) {
            getMenuInflater().inflate(R.menu.spinner, menu);

            MenuItem item = menu.findItem(R.id.spinner);
            Spinner spinner = (Spinner) MenuItemCompat.getActionView(item);

            ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                    R.array.spinner_list_item_array, android.R.layout.simple_spinner_item);
            adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);

            spinner.setAdapter(adapter);
            return true;
        }


        @Override
        public void onListItemClick(int clickedItemIndex) {

            if (mToast != null) {
                mToast.cancel();
            }

            String toastMessage = "Item #" + clickedItemIndex + " clicked.";
            mToast = Toast.makeText(this, toastMessage, Toast.LENGTH_LONG);

            mToast.show();

            Context context = MainActivity.this;  //Note to self: this is assumed, can be condensed to Intent intent = new Intent(MainActivity.this, DetailActivity.this);
            Class destinationActivity = DetailActivity.class;
            Intent intent = new Intent(context, destinationActivity);

            startActivity(intent);
        }

}//MAIN ACTIVITY


//TODO (7) app queries the /movie/popular or /movie/top_rated.

