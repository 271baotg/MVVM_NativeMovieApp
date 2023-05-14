package com.example.nativemovieapp.viewmodel;

import android.renderscript.ScriptGroup;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nativemovieapp.Api.ApiService;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.Repository;
import com.example.nativemovieapp.Api.TMDB;
import com.example.nativemovieapp.AppExecutor;
import com.example.nativemovieapp.Firebase.RealtimeRepository;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;
import com.example.nativemovieapp.Model.Movies;
import com.google.firebase.database.ChildEventListener;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;

public class FavoriteViewModel extends ViewModel {
    Repository DB = Repository.getInstance();
    RealtimeRepository FDB = RealtimeRepository.getInstance();

    private MutableLiveData<List<MovieDetail>> favorList;
    private List<MovieDetail> tempList;

    public MutableLiveData<List<MovieDetail>> getFavorList() {
        return favorList;
    }

    public LiveData<MovieDetail> getMovieDetail() {
        return DB.getMovieDetail();
    }

    public FavoriteViewModel() {
        super();
        favorList = new MutableLiveData<>();
    }

    public void loadListFavor() {

        ExecutorService executor = Executors.newFixedThreadPool(10);
        DatabaseReference ref = FDB.getNode("USERS").child(Credential.getCurrentUser().getUid()).child("FavoriteList");
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                tempList = new ArrayList<MovieDetail>();
                if(!snapshot.exists())
                {
                    favorList.setValue(null);
                }
                else {
                    for (DataSnapshot moviedetail : snapshot.getChildren()) {
                        tempList.add(moviedetail.getValue(MovieDetail.class));
                    }
                }
                favorList.setValue(tempList);
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }
}
