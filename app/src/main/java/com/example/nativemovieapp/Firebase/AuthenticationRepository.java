package com.example.nativemovieapp.Firebase;

import android.net.Uri;
import android.util.Log;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.google.android.gms.tasks.OnCompleteListener;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthCredential;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.EmailAuthCredential;
import com.google.firebase.auth.EmailAuthProvider;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;

public class AuthenticationRepository {
    private static AuthenticationRepository _ins;

    public static AuthenticationRepository getInstance() {
        if (_ins == null) {
            _ins = new AuthenticationRepository();
        }
        return _ins;
    }

    private MutableLiveData<FirebaseUser> userData;
    private MutableLiveData<Boolean> isLogged;
    private FirebaseAuth auth;

    public AuthenticationRepository() {
        userData = new MutableLiveData<>();
        isLogged = new MutableLiveData<>();
        isLogged.setValue(false);
        auth = FirebaseAuth.getInstance();
        if (auth.getCurrentUser() != null) {
            userData.postValue(auth.getCurrentUser());
        }
    }

    public MutableLiveData<FirebaseUser> getUserData() {
        return userData;
    }

    public MutableLiveData<Boolean> getUserLoggedMutableLiveData() {
        return isLogged;
    }

    public void register(String email, String pass, AuthenticationCallBack callback) {
        auth.createUserWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userData.setValue(auth.getCurrentUser());
                    Log.i("test in AR:", auth.getCurrentUser().getEmail());
                    sendEmailVerification(new SendEmailVerificationListener() {
                        @Override
                        public void onCompleted(Task<Void> task) {
                            Log.i("test in AR", "send email");
                        }
                    });
                }
                callback.onRegisterCompleted(task);

            }
        });
    }

    public void login(String email, String pass, AuthenticationRepository.AuthenticationCallBack callBack) {
        auth.signInWithEmailAndPassword(email, pass).addOnCompleteListener(new OnCompleteListener<AuthResult>() {
            @Override
            public void onComplete(@NonNull Task<AuthResult> task) {
                if (task.isSuccessful()) {
                    userData.setValue(auth.getCurrentUser());
                    isLogged.setValue(true);
                    callBack.onLoginCompleted(userData);
                } else {
                    callBack.onLoginCompleted(null);
                }
            }
        });
    }

    public void updateDisplayName(String name) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setDisplayName(name)
                .build();
        userData.getValue().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                });
    }
    public void updatePhotoUri(Uri uri) {
        UserProfileChangeRequest profileUpdates = new UserProfileChangeRequest.Builder()
                .setPhotoUri(uri)
                .build();
        userData.getValue().updateProfile(profileUpdates)
                .addOnCompleteListener(new OnCompleteListener<Void>() {
                    @Override
                    public void onComplete(@NonNull Task<Void> task) {
                        if (task.isSuccessful()) {
                        }
                    }
                });
    }
    public void sendEmailVerification(SendEmailVerificationListener listener)
    {
        auth.getCurrentUser().sendEmailVerification().addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onCompleted(task);
            }
        });
    }
    public void sendEmailResetPassword(String email, SendPasswordResetEmailListener listener)
    {
        auth.sendPasswordResetEmail(email).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onCompleted(task);
            }
        });
    }
    public void updatePassword( String newPassword, UpdatePasswordListener listener)
    {
        auth.getCurrentUser().updatePassword(newPassword).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                listener.onCompleted(task);
            }
        });
    }
    public void reAuthenticate(String password, ReAuthenticateListener listener){
        AuthCredential authCredential = EmailAuthProvider.getCredential(auth.getCurrentUser().getEmail(), password);
        auth.getCurrentUser().reauthenticate(authCredential).addOnCompleteListener(new OnCompleteListener<Void>() {
            @Override
            public void onComplete(@NonNull Task<Void> task) {
                    listener.onCompleted(task);
            }
        });
    }



    public void signOut() {
        auth.signOut();
        //userData.postValue(null);
        isLogged.postValue(false);
    }


    public interface SendEmailVerificationListener{
        void onCompleted(Task<Void> task);
    }
    public interface SendPasswordResetEmailListener{
        void onCompleted(Task<Void> task);
    }

    public interface AuthenticationCallBack {
        void onLoginCompleted(MutableLiveData<FirebaseUser> user);

        void onRegisterCompleted(Task<AuthResult> task);
    }
    public interface UpdatePasswordListener{
        void onCompleted(Task<Void> task);
    }
    public interface ReAuthenticateListener {
        public void onCompleted(Task<Void> task);
    }
}