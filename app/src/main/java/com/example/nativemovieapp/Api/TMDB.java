package com.example.nativemovieapp.Api;

import com.example.nativemovieapp.Model.*;
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

    //https://api.themoviedb.org/3/search/movie?api_key=e9e9d8da18ae29fc430845952232787c&page=1&query=women
    @GET("3/search/movie")
    Call<Movies> getListSearch(@Query("api_key") String key,
                               @Query("page") int page,
                               @Query("query") String query
    );
    //https://api.themoviedb.org/3/movie/297762?api_key=e9e9d8da18ae29fc430845952232787c

    @GET("3/movie/{id}")
    Call<MovieDetail> getMovieById(@Path("id") int id,
                                   @Query("api_key") String key

    );

    @GET("/3/genre/movie/list")
    Call<Categories> getListCategory(@Query("api_key") String key);

    //https://api.themoviedb.org/3/movie/upcoming?api_key=e9e9d8da18ae29fc430845952232787c&language=en-US&page=1

    @GET("3/movie/upcoming")
    Call<Movies> getListUpcoming(@Query("api_key") String key,
                                 @Query("page") int page);

    //https://api.themoviedb.org/3/movie/top_rated?api_key=e9e9d8da18ae29fc430845952232787c&language=en-US&page=1
    @GET("3/movie/top_rated")
    Call<Movies> getListTopRate(@Query("api_key") String key,
                                @Query("page") int page);

    //https://api.themoviedb.org/3/discover/movie?api_key=e9e9d8da18ae29fc430845952232787c&with_genres=18
    @GET("/3/discover/movie")
    Call<Movies> getListByCategory(@Query("api_key") String key,
                                   @Query("with_genres") int idCategory);

    //https://api.themoviedb.org/3/movie/297762/similar?api_key=e9e9d8da18ae29fc430845952232787c&language=en-US&page=1
    @GET("3/movie/{id}/similar")
    Call<Movies> getSimilarMovie(@Path("id") int id,
                                 @Query("api_key") String key,
                                 @Query("page") int page
    );

    //https://api.themoviedb.org/3/movie/787459?api_key=e9e9d8da18ae29fc430845952232787c&append_to_response=videos
    @GET("3/movie/{id}")
    Call<Trailers> getMovieTrailer(@Path("id") int id,
                                   @Query("api_key") String key,
                                   @Query("append_to_response") String append_to_response

    );
    
}
