package com.example.android.popularmoviesproject;

import android.content.Context;
import android.content.Intent;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.view.View.OnClickListener;
import android.widget.TextView;

import com.example.android.popularmoviesproject.utilities.MovieData;
import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static android.R.attr.fastScrollOverlayPosition;
import static android.R.attr.onClick;
import static android.R.attr.thumbPosition;
import static android.R.attr.width;
import static android.content.ContentValues.TAG;

/**
 * Created on 6/8/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    List<MovieData> movieArray = Collections.emptyList();
    MovieData current;
    private Context context;


    //Click Listener
    final private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieListAdapter(Context context, List<MovieData> movieArray , ListItemClickListener listener){
        this.movieArray = movieArray;
        this.context = context;

        //Click Listener
        mOnClickListener = listener;
        viewHolderCount = 0;
    }


    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder (ViewGroup viewGroup, int viewType){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_format, viewGroup, false);

        LayoutInflater inflater = LayoutInflater.from(context);
        MovieListAdapter.ViewHolder viewHolder = new MovieListAdapter.ViewHolder(view);

        viewHolderCount++;
        return viewHolder;

    }


    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder viewHolder, int position) {
        MovieData current = movieArray.get(position);
        viewHolder.textMovieTitle.setText(current.movieTitle);
        viewHolder.textMovieAvgRating.setText("Average user Rating: " + current.movieAvgRating);
        viewHolder.textMovieDate.setText("Release Date: " + current.movieReleaseDate);
        viewHolder.textMoviePlot.setText("Plot Synopsis: " + current.moviePlot);
        viewHolder.setPoster(current.moviePoster);
        //viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));
    }

    //I had setPoster auto create method and setter, but it won't take either
    public void setPoster(String moviePoster) {
        Picasso.with(context).load("https://image.tmdb.org/t/p/w185" + moviePoster)
                .placeholder(R.drawable.sample_0)
                .error(R.drawable.sample_3)
                .centerCrop().into(img);
    }
    public void setMovies(){

    }

   @Override
    public int getItemCount(){

       return movieArray.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        TextView textMovieTitle;
        TextView textMovieAvgRating;
        TextView textMovieDate;
        TextView textMoviePlot;

         ImageView img;

        public ViewHolder(View view) {
            super(view);
            textMovieTitle = (TextView) view.findViewById(R.id.movie_title_dtv);
            textMovieTitle = (TextView) view.findViewById(R.id.cellView_title);
            textMovieAvgRating = (TextView) view.findViewById(R.id.average_rating_dtv);
            textMovieDate = (TextView) view.findViewById(R.id.movie_date_dtv);
            textMoviePlot = (TextView) view.findViewById(R.id.movies_blurb_dtv);
            img = (ImageView) view.findViewById(R.id.cellView_img);
            img = (ImageView) view.findViewById(R.id.main_poster_dtv);
            view.setOnClickListener(this);

        }


     @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }

    }
}
