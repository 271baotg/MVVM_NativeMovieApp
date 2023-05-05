package com.example.nativemovieapp.viewmodel;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.nativemovieapp.Api.Repository;
import com.example.nativemovieapp.Firebase.RealtimeRepository;
import com.example.nativemovieapp.Model.MovieDetail;

public class MovieDetailViewModel extends ViewModel {

    Repository DB = Repository.getInstance();
    RealtimeRepository FDB = RealtimeRepository.getInstance();


    public LiveData<MovieDetail> getMovieDetail() {
        return DB.getMovieDetail();
    }

    public void loadMovieDetail(int id, String api_key) {
        DB.loadMovieDetail(id, api_key);
    }
    public void addToFavoriteList(int id){
        FDB.addToFavoriteList(id);
    }


}
