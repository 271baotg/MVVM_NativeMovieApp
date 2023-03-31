package com.example.nativemovieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.Repository;
import com.example.nativemovieapp.Model.Movie;

import java.util.List;

public class HomeViewModels extends ViewModel {

    Repository DB = Repository.getInstance();

    public LiveData<List<Movie>> getListPopular() {
        return DB.getListPopular();
    }


    public void loadListPopularMovie() {
        DB.loadListPopularMovie(Credential.apiKey, 2);
    }
}
