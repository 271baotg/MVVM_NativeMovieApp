package com.example.nativemovieapp.Api;

import com.google.firebase.auth.FirebaseUser;

public class Credential {

    public static String apiKey = "e9e9d8da18ae29fc430845952232787c";
    public static String baseURL = "https://api.themoviedb.org";

    public static String imgBaseUrl = "https://image.tmdb.org/t/p/original";
    public static String bigImgBaseUrl = "https://image.tmdb.org/t/p/w500";
    public static String query="";
    public static String append_to_response = "videos";
    public static FirebaseUser user = null;

    public static void setCurrentUser(FirebaseUser data) {
        user = data;
    }
    public static FirebaseUser getCurrentUser(){
        return user;
    }

}
