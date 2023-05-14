package com.example.nativemovieapp.adapter;

import android.content.Context;
import androidx.fragment.app.Fragment;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;

public interface RcvInterfce {

    void onMovieClick(Movie movie, int id);

    void onMovieFavorClick(MovieDetail movieDetail);

}
