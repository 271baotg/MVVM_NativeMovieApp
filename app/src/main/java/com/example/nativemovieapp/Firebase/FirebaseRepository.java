package com.example.nativemovieapp.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;

import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.GenericTypeIndicator;
import com.google.firebase.database.Query;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class FirebaseRepository {



    public DatabaseReference getNode(String target) {
        if(target==null)
            return FirebaseDatabase.getInstance().getReference();
        else return FirebaseDatabase.getInstance().getReference().child(target);
    }

    private static FirebaseRepository _ins;
    public static FirebaseRepository getInstance(){
        if(_ins==null)
            _ins = new FirebaseRepository();
         return _ins;
    }


    public static void addToFavoriteList(int idMovie){
        Log.d("Clicked", String.valueOf(idMovie));
        DatabaseReference ref = getInstance().getNode("FavoriteList").child("UID1");
        ref.child(String.valueOf(idMovie)).addListenerForSingleValueEvent(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot snapshot) {
                if(snapshot.exists()&& snapshot.getValue().toString().equals("true")) {
                    Log.d("CheckFavor", String.valueOf(idMovie) + "Đã được xóa khỏi list");
                    ref.child(String.valueOf(idMovie)).setValue("false");
                    return;
                }
                if (snapshot.exists()&& snapshot.getValue().toString().equals("false")) {
                    Log.d("CheckFavor", String.valueOf(idMovie) + "Đã được chuyển lại vào list");
                    ref.child(String.valueOf(idMovie)).setValue("true");
                }
                else {
                    Log.d("CheckFavor", String.valueOf(idMovie) + "Đã được thêm vào list");
                    ref.child(String.valueOf(idMovie)).setValue("true");

                }

            }

            @Override
            public void onCancelled(@NonNull DatabaseError error) {

            }
        });
}
}
