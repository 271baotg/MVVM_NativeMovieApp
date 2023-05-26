package com.example.nativemovieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.Repository;
import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.Model.Movie;

import java.util.List;

public class HomeViewModels extends ViewModel {


    Repository DB = Repository.getInstance();

    public LiveData<List<Movie>> getListPopular() {
        return DB.getListPopular();
    }

    public LiveData<List<Category>> getListCategory() {
        return DB.getListCategory();
    }

    public LiveData<List<Movie>> getListHomeTopRate() {
        return DB.getListTopRate();
    }

    public LiveData<List<Movie>> getListUpcoming() {
        return DB.getListUpcoming();
    }

    public LiveData<List<Movie>> getListMovieByCategory() {
        return DB.getListMovieByCategory();
    }

    public void loadListHomeTopRate() {
        DB.loadListTopRateMovie(Credential.apiKey, 2);
    }

    public void loadListUpComing() {
        DB.loadListUpcomingMovie(Credential.apiKey, 2);
    }

    ;


    public void loadListPopularMovie() {
        DB.loadListPopularMovie(Credential.apiKey, 1);
    }

    public void loadListCategory() {
        DB.loadListCategory(Credential.apiKey);
    }


    public void loadListMovieByCategory(int id, String api_key) {
        DB.loadListMovieByCategory(id, api_key);
    }

    public void setNull() {
        DB.setNull();
    }


}
