package com.example.nativemovieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.Repository;
import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.Model.Movie;

import java.util.HashMap;
import java.util.List;

public class HomeViewModels extends ViewModel {

    Repository DB = Repository.getInstance();


    public LiveData<List<Movie>> getListPopular() {
        return DB.getListPopular();
    }


    public void loadListPopularMovie() {
        DB.loadListPopularMovie(Credential.apiKey, 2);
    }

    public void loadListCategory() {
        DB.loadListCategory(Credential.apiKey);
    }


    public LiveData<List<Category>> getListCategory() {
        return DB.getListCategory();

    }

    public HashMap<Integer, List<Movie>> getMapMovie() {
        return DB.getMapMovie();
    }


}
