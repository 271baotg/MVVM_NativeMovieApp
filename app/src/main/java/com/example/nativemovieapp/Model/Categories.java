package com.example.nativemovieapp.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.List;

public class Categories {

    @SerializedName("genres")
    @Expose
    private List<Category> result;

    public List<Category> getResult() {
        return result;
    }

    @Override
    public String toString() {
        return "Categories{" +
                "result=" + result +
                '}';
    }
}
