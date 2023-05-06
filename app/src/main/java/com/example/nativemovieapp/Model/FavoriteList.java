package com.example.nativemovieapp.Model;

import java.util.List;

public class FavoriteList {

    private String UID;
    private List<Integer> listMovieID;

    public String getUID() {
        return UID;
    }

    public List<Integer> getListMovieID() {
        return listMovieID;
    }

    public FavoriteList(String UID, List<Integer> listMovieID) {
        this.UID = UID;
        this.listMovieID = listMovieID;
    }
}
