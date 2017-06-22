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

import java.io.BufferedReader;
import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;


public class MainActivity extends AppCompatActivity
        implements MovieListAdapter.ListItemClickListener{

    private ImageView oneImage;
    private Toast mToast;
    private TextView movieTitle;
    static List<MovieData> movieArray;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        //Recycle View
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_galleryView);
        recyclerView.setHasFixedSize(true);
        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        MovieListAdapter adapter = new MovieListAdapter(getApplicationContext(),movieArray, this);
        recyclerView.setAdapter(adapter);

        new FetchMovieTask().execute();

    }
//From here to onPostExecutre I'm not sure what I should pass in...or do exactly.

    public class FetchMovieTask extends AsyncTask<URL, Void, String>{

        @Override protected String doInBackground(URL... params){
            URL moviesURL = params[0];

            try{
                String movieResult = NetworkUtil.getResponseFromHttpUrl(moviesURL);
                return movieResult;

            }catch(IOException e){
                e.printStackTrace();
                return null;
            }
        }

        @Override protected void onPostExecute(String movieResult){
        //JSON
        List<MovieData> movieArray = new ArrayList<>();

            try {
                for (int i=0; i <= 15; ++i ) {
                JSONArray jArray = new JSONArray(movieResult);
                JSONObject movieObject = jArray.getJSONObject(i);
                MovieData movieData = new MovieData();
                movieData.moviePoster = movieObject.getString("poster_path");
                movieData.movieAvgRating = movieObject.getInt("vote_average");
                movieData.movieTitle = movieObject.getString("title");
                movieData.movieReleaseDate = movieObject.getString("release_date");
                movieData.moviePlot = movieObject.getString("overview");
                movieArray.add(movieData);
                }//for loop

            } catch (JSONException e) {
                e.printStackTrace();
            }//catch
        }//PostExecute

    }//AsyncTask


    //Actionbar Spinner
    @Override public boolean onCreateOptionsMenu(Menu menu){
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
        Intent intent = new Intent (context, destinationActivity);

        startActivity(intent);
    }

} //MAIN ACTIVITY


//TODO(4) Add Menu items/ sorting (Spinnger aka drop down or menu or toggle)

//TODO (7) In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.

