package com.example.nativemovieapp.Api;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.nativemovieapp.AppExecutor;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.Movies;
import retrofit2.Call;

import java.io.IOException;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.Executor;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LiveDataProvider {

    //LiveData
    private static MutableLiveData<List<Movie>> listPopular;
    private static MutableLiveData<List<Movie>> listFavourite;


    private static LiveDataProvider _ins;


    public static LiveDataProvider getInstance() {
        if (_ins == null)
            _ins = new LiveDataProvider();
        return _ins;
    }

    public LiveDataProvider() {
        listPopular = new MutableLiveData<>();
        listFavourite = new MutableLiveData<>();
    }

    public static LiveData<List<Movie>> getListPopular() {
        return listPopular;
    }


    public void loadListPopularMovie(String api_key, int page) {


        final Future handler = AppExecutor.getInstance().getNetworkIo().submit(new Runnable() {

            TMDB tmdbApi = ApiService.getTmdbApi();
            Call<Movies> call = tmdbApi.getListPopular(api_key, page);

            @Override
            public void run() {
                Movies reponse = null;
                try {
                    reponse = call.execute().body();
                    if (reponse != null) {
                        listPopular.postValue(reponse.getListMovie());
                        Log.d("tag1", listPopular.toString());
                    } else {
                        listPopular.postValue(null);
                        Log.d("failed", "null");
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        });

        AppExecutor.getInstance().getNetworkIo().schedule(new Runnable() {
            @Override
            public void run() {
                handler.cancel(true);
            }
        }, 10000, TimeUnit.MILLISECONDS);

    }


}

    