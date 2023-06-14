package com.example.nativemovieapp.viewmodel;

import android.app.Application;
import android.net.Uri;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nativemovieapp.Firebase.AuthenticationRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

import javax.xml.validation.Validator;

public class AuthenticationViewModel extends AndroidViewModel {
    private AuthenticationRepository authenRepo;
    private MutableLiveData<FirebaseUser> userData;
    private MutableLiveData<Boolean> longgedStatus;

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        authenRepo = AuthenticationRepository.getInstance();
        userData = authenRepo.getUserData();
        longgedStatus = authenRepo.getUserLoggedMutableLiveData();
    }

    public LiveData<FirebaseUser> getUserData() {

        return userData;
    }

    //METHODS
    public void register(String email, String pass, AuthViewModelCallBack callBack) {
        authenRepo.register(email, pass, new AuthenticationRepository.AuthenticationCallBack() {
            @Override
            public void onLoginCompleted(MutableLiveData<FirebaseUser> user) {

            }

            @Override
            public void onRegisterCompleted(Task<AuthResult> task) {
                userData.setValue(authenRepo.getUserData().getValue());
                callBack.onRegisterCompleted(task);
            }
        });
    }
    public void login(String email, String pass, AuthViewModelCallBack callBack) {
        authenRepo.login(email, pass, new AuthenticationRepository.AuthenticationCallBack() {
            @Override
            public void onLoginCompleted(MutableLiveData<FirebaseUser> user) {
                if (user == null) {
                    callBack.onLoginCompleted(null);
                } else {
                    userData.setValue(user.getValue());
                    callBack.onLoginCompleted(getUserData());
                }
            }

            @Override
            public void onRegisterCompleted(Task<AuthResult> task) {

            }
        });
    }
    public void signOut() {

        authenRepo.signOut();
    }
    public void sendEmailVerification(SendEmailVerificationListener listener){
        authenRepo.sendEmailVerification(new AuthenticationRepository.SendEmailVerificationListener() {
            @Override
            public void onCompleted(Task<Void> task) {
                listener.onCompleted(task);
            }
        });
    }
    public void sendEmailResetPassword(String email, SendPasswordResetEmailListener listener){
        authenRepo.sendEmailResetPassword(email, new AuthenticationRepository.SendPasswordResetEmailListener() {
            @Override
            public void onCompleted(Task<Void> task) {
                listener.onCompleted(task);
            }
        });
    }
    public void updateDisplayName(String name) {

        authenRepo.updateDisplayName(name);
    }
    public void updatePhotoUri(Uri uri) {
        authenRepo.updatePhotoUri(uri);

    }
    public void reAuthenticate(String password, ReAuthenticateListener listener){
        authenRepo.reAuthenticate(password, new AuthenticationRepository.ReAuthenticateListener() {
            @Override
            public void onCompleted(Task<Void> task) {
                listener.onCompleted(task);
            }
        });
    }
    public void updatePassword(String newPassword, UpdatePasswordListener listener){
        authenRepo.updatePassword(newPassword, new AuthenticationRepository.UpdatePasswordListener() {
            @Override
            public void onCompleted(Task<Void> task) {
                listener.onCompleted(task);
            }
        });
    }
    //INTERFACES
    public interface SendPasswordResetEmailListener{
        void onCompleted(Task<Void> task);
    }
    public interface SendEmailVerificationListener{
        void onCompleted(Task<Void> task);
    }
    public interface AuthViewModelCallBack {
        void onLoginCompleted(LiveData<FirebaseUser> user);

        void onRegisterCompleted(Task<AuthResult> task);
    }
    public interface ReAuthenticateListener {
        public void onCompleted(Task<Void> task);
    }
    public interface UpdatePasswordListener{
        void onCompleted(Task<Void> task);
    }

}
