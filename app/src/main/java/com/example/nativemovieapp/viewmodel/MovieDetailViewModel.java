package com.example.nativemovieapp.viewmodel;

import android.content.Context;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.Repository;
import com.example.nativemovieapp.Firebase.RealtimeRepository;
import com.example.nativemovieapp.Model.Movie;
import com.example.nativemovieapp.Model.MovieDetail;
import com.example.nativemovieapp.Model.MovieTrailer;
import com.example.nativemovieapp.Model.Trailers;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.like.LikeButton;

import java.util.List;

public class MovieDetailViewModel extends ViewModel {

    Repository DB = Repository.getInstance();
    RealtimeRepository FDB = RealtimeRepository.getInstance();

    public  boolean favoriteState;
    int id;
    Context context;
    public int getId(){
        return id;
    }
    public void setId(int Id){
        id=Id;
    }

    public Context getContext(){
        return context;
    }
    public void setContext(Context Context){
        context = Context;
    }

    public LiveData<MovieDetail> getMovieDetail() {
        return DB.getMovieDetail();
    }

    public void loadMovieDetail(int id, String api_key) {
        DB.loadMovieDetail(id, api_key);
    }
    public void addToFavoriteList(int id){
        FDB.addToFavoriteList(id);
    }

    public void removeFromFavoriteList(int id){
        FDB.removeFromFavoriteList(id);
    }

    public void setFavoriteState(int id, LikeButton likeButton){
        DatabaseReference ref = FDB.getNode("USERS").child(Credential.getCurrentUser().getUid()).child("FavoriteList");
        ref.child(String.valueOf(id)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()){
                    likeButton.setLiked(true);
                    Log.d("SetLike1", String.valueOf(id) + snapshot.getValue());
                }
                if(!snapshot.exists()){
                    likeButton.setLiked(false);
                    Log.d("SetLike2", String.valueOf(id) + snapshot.getValue());
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
    }


    public LiveData<List<Movie>> getsimilarMovie() {
        return DB.getListSimilarMovie();
    }
    public void loadListSimilarMovie(int id) {
        DB.loadListSimilarMovie(id, Credential.apiKey, 1);
    }

    public LiveData<List<MovieTrailer>> getMovieTrailers() {
        return DB.getMovieTrailer();
    }
    public void loadMovieTrailers(int id) {
        DB.loadListMovieTrailer(id, Credential.apiKey, Credential.append_to_response);
    }
}
