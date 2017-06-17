package com.example.android.popularmoviesproject;

import java.util.ArrayList;

/**
 * Created on 6/8/2017.
 */

public class CreateList {

    private String movie_title;
    private Integer image_id;

    public String getImage_title() {
        return movie_title;
    }

    public void setImage_title(String movie_name) {
        this.movie_title = movie_name;
    }

    public Integer getImage_ID() {
        return image_id;
    }


    public void setImage_ID(Integer poster_url) {

        this.image_id = poster_url;
    }

}
