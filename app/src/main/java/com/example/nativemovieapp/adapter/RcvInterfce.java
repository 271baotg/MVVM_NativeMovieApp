package com.example.nativemovieapp.adapter;

import androidx.fragment.app.Fragment;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;

public interface RcvInterfce {

    void onMovieClick(Movie movie);

    void onMovieFavorClick(MovieDetail movieDetail);

}
