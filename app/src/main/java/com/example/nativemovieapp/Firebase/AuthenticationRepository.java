package com.example.nativemovieapp.Firebase;

import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationRepository {
    private static AuthenticationRepository _ins;
    public static AuthenticationRepository getInstance() {
        if (_ins == null) {
            _ins = new AuthenticationRepository();
        }
        return _ins;
    }
    private MutableLiveData<FirebaseUser> firebaseUserMutableLiveData;
    private MutableLiveData<Boolean> userLoggedMutableLiveData;
    private FirebaseAuth auth;
    public AuthenticationRepository() {
        firebaseUserMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData = new MutableLiveData<>();
        userLoggedMutableLiveData.setValue(false);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            firebaseUserMutableLiveData.postValue(auth.getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getFirebaseUserMutableLiveData() {
        return firebaseUserMutableLiveData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return userLoggedMutableLiveData;
    }

    public void register(String email, String pass) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.postValue(auth.getCurrentUser());

                } else {

                }

            }
        });
    }

    public void login(String email, String pass, AuthenticationRepository.AuthenticationCallBack callBack) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    firebaseUserMutableLiveData.setValue(auth.getCurrentUser());
                    userLoggedMutableLiveData.setValue(true);
                    callBack.onLoginCompleted(firebaseUserMutableLiveData);
                    Log.i("Test login in AR", getFirebaseUserMutableLiveData().getValue().getUid());
                } else {
                    callBack.onLoginCompleted(null);
                }
            }
        });
    }
    public void signOut(){
        auth.signOut();
        userLoggedMutableLiveData.postValue(true);
    }
    public interface AuthenticationCallBack
    {
        void onLoginCompleted(MutableLiveData<FirebaseUser> user);
    }




}
