package com.example.nativemovieapp.Api;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.nativemovieapp.Model.Movie;

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

    public void loadListPopularMovie(String api_key, int page) {
        LDP.loadListPopularMovie(api_key, page);
    }
}
