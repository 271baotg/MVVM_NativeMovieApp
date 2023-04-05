package com.example.nativemovieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.Repository;
import com.example.nativemovieapp.Model.Movie;

import java.util.List;

public class SearchViewModels extends ViewModel {
    Repository DB = Repository.getInstance();

    public LiveData<List<Movie>> getListSearch() {
        return DB.getListSearch();
    }


    public void loadListSearchMovie(String query) {
        DB.loadListSearchMovie(Credential.apiKey, 1,query);
    }

    public LiveData<List<Movie>> getListUpcoming() {
        return DB.getListUpcoming();
    }

    public void loadListUpcomingMovie() {
        DB.loadListUpcomingMovie(Credential.apiKey, 1);
    }
}
