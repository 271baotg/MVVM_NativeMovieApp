package com.example.nativemovieapp.Api;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;

import java.util.List;

public class Repository {

    private static Repository _ins;

    //Instance for LiveDataProvider
    private static LiveDataProvider LDP;

    public static Repository getInstance() {
        if (_ins == null) {
            _ins = new Repository();
        }
        return _ins;
    }

    private Repository() {
        LDP = new LiveDataProvider();
    }

    public LiveData<List<Movie>> getListPopular() {
        return LiveDataProvider.getListPopular();
    }

    public LiveData<List<Category>> getListCategory() {
        return LDP.getListCategory();
    }


    public void loadListPopularMovie(String api_key, int page) {
        LDP.loadListPopularMovie(api_key, page);
    }


    public LiveData<List<Movie>> getListSearch() {
        return LiveDataProvider.getListSearch();
    }

    public void loadListSearchMovie(String api_key, int page, String query, Context context) {
        LDP.loadListSearch(api_key, page, query, context);
    }

    public LiveData<List<Movie>> getListUpcoming() {
        return LiveDataProvider.getListUpcoming();
    }

    public void loadListUpcomingMovie(String api_key, int page) {
        LDP.loadListUpcoming(api_key, page);
    }

    public LiveData<List<Movie>> getListTopRate() {
        return LiveDataProvider.getListTopRate();
    }

    public void loadListTopRateMovie(String api_key, int page) {
        LDP.loadListTopRate(api_key, page);
    }

    public void loadListCategory(String api_key) {
        LDP.loadListCategory(api_key);
    }


    public void loadMovieDetail(int id, String api_key) {
        LDP.loadMovieDetail(id, api_key);
    }

    public LiveData<MovieDetail> getMovieDetail() {
        return LDP.getMovieDetail();
    }
}

