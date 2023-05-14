package com.example.nativemovieapp.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.example.nativemovieapp.Api.ApiService;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.TMDB;
import com.example.nativemovieapp.Model.MovieDetail;
import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

import retrofit2.Call;

public class RealtimeRepository {
    public DatabaseReference getNode(String target) {
        if(target==null)
            return FirebaseDatabase.getInstance().getReference();
        else return FirebaseDatabase.getInstance().getReference().child(target);
    }

    private static RealtimeRepository _ins;
    public static RealtimeRepository getInstance(){
        if(_ins==null)
            _ins = new RealtimeRepository();
        return _ins;
    }


//    public static void addToFavoriteList(int idMovie){
//        Log.d("Clicked", String.valueOf(idMovie));
//        DatabaseReference ref = getInstance().getNode("USERS").child(Credential.getCurrentUser().getUid()).child("FavoriteList");
//        ref.child(String.valueOf(idMovie)).addListenerForSingleValueEvent(new ValueEventListener() {
//            @Override
//            public void onDataChange(@NonNull DataSnapshot snapshot) {
//                if(snapshot.exists()&& snapshot.getValue().toString().equals("true")) {
//                    Log.d("CheckFavor", String.valueOf(idMovie) + "Đã được xóa khỏi list");
//                    ref.child(String.valueOf(idMovie)).setValue("false");
//                    return;
//                }
//                if (snapshot.exists()&& snapshot.getValue().toString().equals("false")) {
//                    Log.d("CheckFavor", String.valueOf(idMovie) + "Đã được chuyển lại vào list");
//                    ref.child(String.valueOf(idMovie)).setValue("true");
//                }
//                else {
//                    Log.d("CheckFavor", String.valueOf(idMovie) + "Đã được thêm vào list");
//                    ref.child(String.valueOf(idMovie)).setValue("true");
//
//                }
//
//            }
//            @Override
//            public void onCancelled(@NonNull DatabaseError error) {
//
//            }
//        });
//    }

    public void addToFavoriteList(int idMovie) {
        Log.d("Clicked", "addToFavoriteList: ");
        DatabaseReference ref = getInstance().getNode("USERS").child(Credential.getCurrentUser().getUid()).child("FavoriteList");
        ExecutorService executor = Executors.newFixedThreadPool(10);
        ref.child(String.valueOf(idMovie)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if (!snapshot.exists()) {
                    MovieDetail temp;
                    TMDB tmdb = ApiService.getTmdbApi();
                    Call<MovieDetail> call = tmdb.getMovieById(idMovie, Credential.apiKey);
                    Future<MovieDetail> movie = executor.submit(new Callable<MovieDetail>() {
                        @Override
                        public MovieDetail call() throws Exception {
                            return call.execute().body();
                        }
                    });
                    try {
                        temp = movie.get();
                        ref.child(String.valueOf(idMovie)).setValue(temp);
                    } catch (Exception e) {
                        throw new RuntimeException(e);
                    }
                }
            }
            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
        }

    public void removeFromFavoriteList(int idMovie){
        DatabaseReference ref = getInstance().getNode("USERS").child(Credential.getCurrentUser().getUid()).child("FavoriteList").child(String.valueOf(idMovie));
        ref.removeValue().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                Log.d("Đã xóa" , String.valueOf(idMovie));
            }
        });
    }


    }

