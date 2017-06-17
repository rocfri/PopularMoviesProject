//gallery tutorial androidauthority how to build an image gallery app
package com.example.android.popularmoviesproject;

import android.content.Context;
import android.content.Intent;
import android.os.AsyncTask;
import android.support.v4.view.MenuItemCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.GridLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.ArrayAdapter;
import android.widget.GridLayout;
import android.widget.ImageView;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;

import com.example.android.popularmoviesproject.utilities.NetworkUtil;

import java.io.IOException;
import java.net.URL;
import java.util.ArrayList;
import java.util.Arrays;


public class MainActivity extends AppCompatActivity
        implements MovieListAdapter.ListItemClickListener{

    private ImageView oneImage;
    private Toast mToast;
    private TextView movieTitle;
    static ArrayList<String> posters;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ImageView oneImage = (ImageView) findViewById(R.id.imageView);
        //Recycle View
        RecyclerView recyclerView = (RecyclerView)findViewById(R.id.rv_galleryView);
        recyclerView.setHasFixedSize(true);
        //LayoutManager
        RecyclerView.LayoutManager layoutManager = new GridLayoutManager(getApplicationContext(),3);
        recyclerView.setLayoutManager(layoutManager);

        ArrayList<CreateList> movieArray = prepareData();
        //Don't understand why "this" works as a parameter here.
        MovieListAdapter adapter = new MovieListAdapter(getApplicationContext(), movieArray, this);
        recyclerView.setAdapter(adapter);

    }
//From here to onPostExecutre I'm not sure what I should pass in...or do exactly.
    private void loadMovieData(){
        URL movieRequestURL = NetworkUtil.buildURL(mdbSortQuery);
        new FetchMovieTask().execute(movieRequestURL);
    }

    public class FetchMovieTask extends AsyncTask<URL, Void, String>{

        @Override protected String doInBackground(URL... params){

            String moviesURL = params[0];

            try{
                movieQueryResults = NetworkUtil.getResponseFromHttpUrl(moviesURL);
            }catch(IOException e){
                e.printStackTrace();
                return null;
            }


        }

        @Override protected onPostExecute(String[] movieData ){
//Not sure how to get array list passed into here and then call prepareData().
        }

    }


    final private Integer mPics[] ={
            R.drawable.sample_0,
            R.drawable.sample_1,
            R.drawable.sample_2,
            R.drawable.sample_3,
            R.drawable.sample_4,
            R.drawable.sample_5,
            R.drawable.sample_6,
            R.drawable.sample_7,
    };

    private ArrayList<CreateList> prepareData() {
        ArrayList<CreateList> theImages = new ArrayList<>();
        for (int i = 0; i < 20; i++){
            CreateList createList = new CreateList();
            createList.setImage_ID(mPics[i]);
            theImages.add(createList);
        }
        return theImages;

    }



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

}

//TODO(2) Details include Title, release date, movie poster, vote average and plot summary

//TODO(3)Connect main to detail activity and intent
//TODO(4) Add Menu items/ sorting (Spinnger aka drop down or menu or toggle)

//TODO(5) Connect API match components
//TODO(6) Give upward nav in manifest by making Detail child of Main.
//TODO (7) In a background thread, app queries the /movie/popular or /movie/top_rated API for the sort criteria specified in the settings menu.

//TODO(EXTRA) sort best movies of 90s (Nastalgia)
