package com.example.nativemovieapp.viewmodel;

import android.util.Log;
import android.widget.EditText;
import androidx.annotation.NonNull;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Api.Repository;
import com.example.nativemovieapp.Firebase.RealtimeRepository;
import com.example.nativemovieapp.Model.Comment;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.ValueEventListener;
import org.jetbrains.annotations.NotNull;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

public class CommentViewModel extends ViewModel {
    RealtimeRepository FDB = RealtimeRepository.getInstance();
    FirebaseUser user;

    public CommentViewModel() {
        listComment = new MutableLiveData<>();
    }

    private MutableLiveData<List<Comment>> listComment;
    private List<Comment> tempList;

    public MutableLiveData<List<Comment>> getListComment() {
        return listComment;
    }

    public void sendComment(int idMovie, EditText input) {

        String img;
        user = Credential.getCurrentUser();
        if (user.getPhotoUrl() != null)
            img = user.getPhotoUrl().toString();
        else img = "null";
        Comment comment = new Comment(img, user.getDisplayName(), user.getUid(), input.getText().toString());
        DatabaseReference ref = FDB.getNode("COMMENTS").child(String.valueOf(idMovie));
        ref.push().setValue(comment);
        Log.d("cmt", "Comment Added");
    }

    public void loadComment(int idMovie) {
        DatabaseReference ref = FDB.getNode("COMMENTS").child(String.valueOf(idMovie));
        ref.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull @NotNull DataSnapshot snapshot) {
                tempList = new ArrayList<Comment>();
                if (!snapshot.exists())
                    listComment.setValue(null);
                else {
                    for (DataSnapshot dataSnapshot : snapshot.getChildren())
                        tempList.add(dataSnapshot.getValue(Comment.class));
                }
                listComment.postValue(tempList);
            }

            @Override
            public void onCancelled(@NonNull @NotNull DatabaseError error) {

            }
        });
    }

}
