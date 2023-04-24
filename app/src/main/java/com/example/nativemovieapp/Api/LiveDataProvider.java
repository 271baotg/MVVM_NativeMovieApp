package com.example.nativemovieapp.Api;

import android.content.Context;
import android.util.Log;
import android.widget.Toast;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import com.example.nativemovieapp.AppExecutor;
import com.example.nativemovieapp.Model.*;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;

public class LiveDataProvider {

    //LiveData
    private static MutableLiveData<List<Movie>> listPopular;

    private static MutableLiveData<MovieDetail> movieDetail;
    private static MutableLiveData<List<Category>> listCategory;

    private static MutableLiveData<List<Movie>> listSearch;
    private static MutableLiveData<List<Movie>> listUpcoming;
    private static MutableLiveData<Movie> itemMovie;
    private static MutableLiveData<List<Movie>> listTopRate;

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
        listSearch = new MutableLiveData<>();
        listUpcoming = new MutableLiveData<>();
        listCategory = new MutableLiveData<>();
        listTopRate = new MutableLiveData<>();
        movieDetail = new MutableLiveData<>();

    }


    public static MutableLiveData<MovieDetail> getMovieDetail() {
        return movieDetail;
    }

    public static LiveData<List<Movie>> getListPopular() {
        return listPopular;
    }

    public static LiveData<List<Movie>> getListSearch() {
        return listSearch;
    }

    public static LiveData<List<Movie>> getListUpcoming() {
        return listUpcoming;
    }

    public static LiveData<List<Movie>> getListTopRate() {
        return listTopRate;
    }

    public static List<Movie> movieListFinal = new ArrayList<>();

    public static LiveData<List<Category>> getListCategory() {
        return listCategory;
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

    public static void loadListSearch(String api_key, int page, String query, Context context) {

        final Future handler = AppExecutor.getInstance().getNetworkIo().submit(new Runnable() {

            TMDB tmdbApi = ApiService.getTmdbApi();
            Call<Movies> call = tmdbApi.getListSearch(api_key, page, query);

            @Override
            public void run() {
                Movies reponse = null;
                try {
                    reponse = call.execute().body();
                    if (reponse.getListMovie().isEmpty() == false) {
                        List<Movie> movie = reponse.getListMovie();
                        for (Movie item : movie) {
                            if (item.getImageURL() != null) {
                                movieListFinal.add(item);
                            }
                        }
                        listSearch.postValue(movieListFinal);
                        Log.d("tag1", listSearch.toString());
                    } else {
                        listSearch.postValue(null);
                        Toast.makeText(context, "Results found do not match your search term", Toast.LENGTH_SHORT).show();
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

    public void loadListUpcoming(String api_key, int page) {


        final Future handler = AppExecutor.getInstance().getNetworkIo().submit(new Runnable() {

            TMDB tmdbApi = ApiService.getTmdbApi();
            Call<Movies> call = tmdbApi.getListUpcoming(api_key, page);

            @Override
            public void run() {
                Movies reponse = null;
                try {
                    reponse = call.execute().body();
                    if (reponse != null) {
                        listUpcoming.postValue(reponse.getListMovie());
                        Log.d("tag1", listUpcoming.toString());
                    } else {
                        listUpcoming.postValue(null);
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

    public void loadListTopRate(String api_key, int page) {


        final Future handler = AppExecutor.getInstance().getNetworkIo().submit(new Runnable() {

            TMDB tmdbApi = ApiService.getTmdbApi();
            Call<Movies> call = tmdbApi.getListTopRate(api_key, page);

            @Override
            public void run() {
                Movies reponse = null;
                try {
                    reponse = call.execute().body();
                    if (reponse != null) {
                        listTopRate.postValue(reponse.getListMovie());
                        Log.d("tag1", listTopRate.toString());
                    } else {
                        listTopRate.postValue(null);
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


    public void loadListCategory(String api_key) {

        final Future handler = AppExecutor.getInstance().getNetworkIo().submit(new Runnable() {
            TMDB tmdbApi = ApiService.getTmdbApi();
            Call<Categories> call = tmdbApi.getListCategory(api_key);

            @Override
            public void run() {
                Categories response = null;
                try {
                    response = call.execute().body();
                    if (response != null) {
                        listCategory.postValue(response.getResult());

                    } else {
                        listCategory.postValue(null);
                    }
                } catch (IOException e) {
                    throw new RuntimeException(e);
                }

            }

        });
    }

    public void loadMovieDetail(int id, String api_key) {
        TMDB tmdb = ApiService.getTmdbApi();
        Call<MovieDetail> call = tmdb.getMovieById(id, api_key);

        ExecutorService executor = Executors.newFixedThreadPool(3);

        final Future handler = executor.submit(new Runnable() {
            @Override
            public void run() {
                MovieDetail response = null;
                try {
                    response = call.execute().body();
                    if (response != null) {
                        movieDetail.postValue(response);
                        Log.d("Detail", response.toString());
                    } else {
                        movieDetail.postValue(null);
                        Log.d("Detail null", "null");
                    }

                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
            }


        });
    }

}
        

    