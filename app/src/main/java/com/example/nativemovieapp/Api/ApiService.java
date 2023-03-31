package com.example.nativemovieapp.Api;

import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ApiService {

    private static Retrofit.Builder retrofitBuilder = new Retrofit.Builder()
            .baseUrl(Credential.baseURL)
            .addConverterFactory(GsonConverterFactory.create());

    private static Retrofit retrofit = retrofitBuilder.build();

    private static TMDB tmdbApi = retrofit.create(TMDB.class);

    public static TMDB getTmdbApi() {
        return tmdbApi;
    }
}
