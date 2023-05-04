package com.example.nativemovieapp.Firebase;

import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

public class RealtimeRepository {
    private static RealtimeRepository _ins;

    public static RealtimeRepository getInstance() {
        if (_ins == null) {
            _ins = new RealtimeRepository();
        }
        return _ins;
    }

    private DatabaseReference databaseReference;

    public RealtimeRepository() {
        databaseReference = FirebaseDatabase.getInstance().getReference();
    }




}
