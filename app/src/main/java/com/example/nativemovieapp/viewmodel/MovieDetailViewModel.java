package com.example.nativemovieapp.viewmodel;

import android.content.Context;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.ViewModel;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.Repository;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;
import com.example.nativemovieapp.Model.MovieTrailer;
import com.example.nativemovieapp.Model.Trailers;

import java.util.List;

public class MovieDetailViewModel extends ViewModel {

    Repository DB = Repository.getInstance();

    int id;
    Context context;
    public int getId(){
        return id;
    }
    public void setId(int Id){
        id=Id;
    }

    public Context getContext(){
        return context;
    }
    public void setContext(Context Context){
        context = Context;
    }

    public LiveData<MovieDetail> getMovieDetail() {
        return DB.getMovieDetail();
    }

    public void loadMovieDetail(int id, String api_key) {
        DB.loadMovieDetail(id, api_key);
    }

    public LiveData<List<Movie>> getsimilarMovie() {
        return DB.getListSimilarMovie();
    }
    public void loadListSimilarMovie(int id) {
        DB.loadListSimilarMovie(id, Credential.apiKey, 1);
    }

    public LiveData<List<MovieTrailer>> getMovieTrailers() {
        return DB.getMovieTrailer();
    }
    public void loadMovieTrailers(int id) {
        DB.loadListMovieTrailer(id, Credential.apiKey, Credential.append_to_response);
    }
}
