package com.example.nativemovieapp.Api;

import android.util.Log;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.nativemovieapp.AppExecutor;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.Movies;
import retrofit2.Call;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Future;
import java.util.concurrent.TimeUnit;

public class LiveDataProvider {

    //LiveData
    private static MutableLiveData<List<Movie>> listPopular;
    private static MutableLiveData<List<Movie>> listFavourite;

    private static MutableLiveData<List<Movie>> listSearch;

    private static MutableLiveData<List<Movie>> listSearchConvert;
    private  static MutableLiveData<Movie> itemMovie;

    private static LiveDataProvider _ins;


    public static LiveDataProvider getInstance() {
        if (_ins == null)
            _ins = new LiveDataProvider();
        return _ins;
    }

    public LiveDataProvider() {
        listPopular = new MutableLiveData<>();
        listFavourite = new MutableLiveData<>();
        listSearch = new MutableLiveData<>();
        listSearchConvert=new MutableLiveData<>();
    }

    public static LiveData<List<Movie>> getListPopular() {
        return listPopular;
    }
    public static LiveData<List<Movie>> getListSearch() {
        return  listSearch;
    }
    public static LiveData<List<Movie>> getListSearchConvert() {
        return  listSearchConvert;
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

    public static void loadListSearch(String api_key, int page, String query) {

        final Future handler = AppExecutor.getInstance().getNetworkIo().submit(new Runnable() {

            TMDB tmdbApi = ApiService.getTmdbApi();
            Call<Movies> call = tmdbApi.getListSearch(api_key, page,query);

            @Override
            public void run() {
                Movies reponse = null;
                try {
                    reponse = call.execute().body();
                    if (reponse != null) {
                        List<Movie> movie = reponse.getListMovie();
                        loadListSearchConvert(api_key,movie);
                        Log.d("tag1", listSearch.toString());
                    } else {
                        listSearch.postValue(null);
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
    public static List<Movie> movieListFinal=new ArrayList<>();
    public static void loadListSearchConvert(String api_key, List<Movie> movie) {

        List<Movie> listMovie = new ArrayList<>();
        for (Movie item : movie) {
            if(item.getImageURL()!=null){
                int id = item.getId();
                final Future handler = AppExecutor.getInstance().getNetworkIo().submit(new Runnable() {

                    TMDB tmdbApi = ApiService.getTmdbApi();

                    Call<Movie> call = tmdbApi.getItemMovie(id,api_key);

                    @Override
                    public void run() {
                        Movie reponse = null;
                        try {
                            reponse = call.execute().body();
                            if (reponse != null) {
                                synchronized (listMovie) {
                                    listMovie.add(reponse);
                                    movieListFinal.add(reponse);
                                }
                                Log.d("tag1", listMovie.toString());
                            } else {
                                listSearchConvert.postValue(null);
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

        // Wait for all API calls to complete
        AppExecutor.getInstance().getNetworkIo().submit(new Runnable() {
            @Override
            public void run() {
                synchronized (listMovie) {
                    listSearch.postValue(listMovie);
                }
            }
        });

    }


}

    