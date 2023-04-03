package com.example.nativemovieapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

//Class dành cho thông tin trả về của 1 trang phim
public class Movies {
    private int page;

    //Mảng này gồm thông tin nhiều bộ phim
    @SerializedName("results")
    @Expose
    List<Movie> listMovie;


    public void setPage(int page) {
        this.page = page;
    }

    public void setListMovie(List<Movie> listMovie) {
        this.listMovie = listMovie;
    }

    public int getPage() {
        return page;
    }


    @Override
    public String toString() {
        return "Movies{" +
                "page=" + page +
                ", listMovie=" + listMovie +
                '}';
    }

    public List<Movie> getListMovie() {
        return listMovie;
    }
}
