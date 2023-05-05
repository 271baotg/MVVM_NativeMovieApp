package com.example.nativemovieapp.viewmodel;

import android.app.Application;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import com.example.nativemovieapp.Firebase.AuthenticationRepository;
import com.example.nativemovieapp.Firebase.RealtimeRepository;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;

public class AuthenticationViewModel extends AndroidViewModel {
    private AuthenticationRepository authenRepo;
    private MutableLiveData<FirebaseUser> userData;
    private MutableLiveData<Boolean> longgedStatus;

    public AuthenticationViewModel(@NonNull Application application) {
        super(application);
        authenRepo = AuthenticationRepository.getInstance();
         userData = authenRepo.getFirebaseUserMutableLiveData();
         longgedStatus = authenRepo.getUserLoggedMutableLiveData();
    }
    public LiveData<FirebaseUser> getUserData() {return AuthenticationRepository.getInstance().getFirebaseUserMutableLiveData();}
    public void register(String email, String pass, AuthViewModelCallBack callBack) {
        authenRepo.register(email, pass, new AuthenticationRepository.AuthenticationCallBack() {
            @Override
            public void onLoginCompleted(MutableLiveData<FirebaseUser> user) {

            }

            @Override
            public void onRegisterCompleted(Task<AuthResult> task) {
                 callBack.onRegisterCompleted(task);
            }
        });
    }
    public void login(String email, String pass, AuthViewModelCallBack callBack){
        authenRepo.login(email, pass, new AuthenticationRepository.AuthenticationCallBack() {
            @Override
            public void onLoginCompleted(MutableLiveData<FirebaseUser> user) {
                if(user == null){
                    callBack.onLoginCompleted(null);
                }else {
                    userData.setValue(user.getValue());
                    callBack.onLoginCompleted(getUserData());
                }
            }

            @Override
            public void onRegisterCompleted(Task<AuthResult> task) {

            }
        });
    }
    public void update()
    {
        authenRepo.update();
    }

    public interface AuthViewModelCallBack{
        void onLoginCompleted(LiveData<FirebaseUser> user);
        void onRegisterCompleted(Task<AuthResult> task);
    }


}
