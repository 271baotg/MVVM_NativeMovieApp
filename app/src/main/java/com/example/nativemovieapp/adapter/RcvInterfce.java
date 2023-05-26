package com.example.nativemovieapp.adapter;

import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;

public interface RcvInterfce {

    void onMovieClick(Movie movie, int id);

    void onMovieFavorClick(MovieDetail movieDetail);

    default void onCategoryClick(Category category) {
        onCategoryClick(category);
    }


}
