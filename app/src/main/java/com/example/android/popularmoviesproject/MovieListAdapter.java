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
import android.widget.Toast;

import com.squareup.picasso.Picasso;

import java.lang.reflect.Array;
import java.util.ArrayList;

import static android.R.attr.onClick;
import static android.R.attr.width;
import static android.content.ContentValues.TAG;

/**
 * Created on 6/8/2017.
 */

public class MovieListAdapter extends RecyclerView.Adapter<MovieListAdapter.ViewHolder> {

    private ArrayList<CreateList> movieArray;
    private Context context;

    //Click Listener
    final private ListItemClickListener mOnClickListener;
    private static int viewHolderCount;
    private int mNumberItems;

    public interface ListItemClickListener {
        void onListItemClick(int clickedItemIndex);
    }

    public MovieListAdapter(Context context, ArrayList<String> imgArray, ListItemClickListener listener){
        this.movieArray = movieArray;
        this.context = context;

        //Click Listener
        mNumberItems = imgArray.size();
        mOnClickListener = listener;
        viewHolderCount = 0;
    }


    @Override
    public MovieListAdapter.ViewHolder onCreateViewHolder (ViewGroup viewGroup, int i){
        View view = LayoutInflater.from(viewGroup.getContext()).inflate(R.layout.cell_format, viewGroup, false);

        LayoutInflater inflater = LayoutInflater.from(context);
        MovieListAdapter.ViewHolder viewHolder = new MovieListAdapter.ViewHolder(view);

        viewHolderCount++;
        return new ViewHolder(view);

    }

    @Override
    public void onBindViewHolder(MovieListAdapter.ViewHolder viewHolder, int i){

        //viewHolder.img.setScaleType(ImageView.ScaleType.CENTER_CROP);
        //viewHolder.img.setImageResource((galleryList.get(i).getImage_ID()));
        Picasso.with(context).load("https://image.tmdb.org/t/p/w185/" + ).resize(width, (int) (width*1.5))
                .centerCrop().into(viewHolder.img);
    }



    @Override
    public int getItemCount(){

        return mNumberItems;
    }

    public class ViewHolder extends RecyclerView.ViewHolder implements OnClickListener {

        private TextView title;
        private ImageView img;

        public ViewHolder(View view) {
            super(view);
            title = (TextView) view.findViewById(R.id.textView_title);
            img = (ImageView) view.findViewById(R.id.imageView_thmb);
            view.setOnClickListener(this);

        }


        @Override
        public void onClick(View v) {
            int clickedPosition = getAdapterPosition();
            mOnClickListener.onListItemClick(clickedPosition);

        }
    }
}
