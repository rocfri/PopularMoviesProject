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
import static com.example.android.popularmoviesproject.R.id.cellView_img;

/**
 * Created on 6/8/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    List movieArray = Collections.emptyList();
    MovieData current;
    private Context context;



    //Click Listener
    final private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public void setMovies(List movieArray) {
        this.movieArray = movieArray;
        this.notifyDataSetChanged();
    };


    public MovieListAdapter(Context context, List movieArray , ListItemClickListener listener){
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
        movieArray.get(position);
        viewHolder.textMovieTitle.setText(current.movieTitle);
        viewHolder.textMovieAvgRating.setText("Average user Rating: " + current.movieAvgRating);
        viewHolder.textMovieDate.setText("Release Date: " + current.movieReleaseDate);
        viewHolder.textMoviePlot.setText("Plot Synopsis: " + current.moviePlot);
        viewHolder.setPoster(current.moviePoster);
        //viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));

    }


   @Override
    public int getItemCount(){

       if (movieArray == null) {
           return 0;
       } else {
           return movieArray.size();
       }
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        TextView textMovieTitle;
        TextView textMovieAvgRating;
        TextView textMovieDate;
        TextView textMoviePlot;
         ImageView img;

        public void setPoster(String p) {
            Picasso.with(context).load("https://image.tmdb.org/t/p/w185" + p)
                    .into(img);
        }

        public ViewHolder(View view) {
            super(view);
            textMovieTitle = (TextView) view.findViewById(R.id.movie_title_dtv);
            textMovieTitle = (TextView) view.findViewById(R.id.cellView_title);
            textMovieAvgRating = (TextView) view.findViewById(R.id.average_rating_dtv);
            textMovieDate = (TextView) view.findViewById(R.id.movie_date_dtv);
            textMoviePlot = (TextView) view.findViewById(R.id.movies_blurb_dtv);
           img = (ImageView) view.findViewById(R.id.cellView_img);
            view.setOnClickListener(this);

        }


     @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }

    }
}
