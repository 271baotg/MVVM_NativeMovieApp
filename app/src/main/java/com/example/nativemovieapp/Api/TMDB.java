package com.example.nativemovieapp.Api;

import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.Categories;
import com.example.nativemovieapp.Model.Category;
import com.example.nativemovieapp.Model.Movies;
import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Path;
import retrofit2.http.Query;

import java.util.List;

public interface TMDB {


    //https://api.themoviedb.org/3/movie/popular?api_key=e9e9d8da18ae29fc430845952232787c&page=1
    @GET("/3/movie/popular")
    Call<Movies> getListPopular(@Query("api_key") String key,
                                @Query("page") int page
    );

    //https://api.themoviedb.org/3/search/movie/550?api_key=e9e9d8da18ae29fc430845952232787c&page=1&query=women
    @GET("3/search/movie")
    Call<Movies> getListSearch(@Query("api_key") String key,
                                @Query("page") int page,
                               @Query("query") String query
    );
    //https://api.themoviedb.org/3/movie/297762?api_key=e9e9d8da18ae29fc430845952232787c

    @GET("3/movie/{id}")
    Call<Movie> getItemMovie(@Path("id") int id,
                             @Query("api_key") String key

    );

    @GET("/3/genre/movie/list")
    Call<Categories> getListCategory(@Query("api_key") String key);

}
