package com.example.nativemovieapp;

import androidx.appcompat.app.AppCompatActivity;
import androidx.lifecycle.ViewModelProvider;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.content.Intent;
import android.os.Bundle;

import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.Firebase.AuthenticationRepository;
import com.example.nativemovieapp.viewmodel.AuthenticationViewModel;

public class AuthenticationActivity extends AppCompatActivity {

    private NavController navController;
    private AuthenticationViewModel authViewModel;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authViewModel = new ViewModelProvider(this).get(AuthenticationViewModel.class);
        setContentView(R.layout.activity_authentication);
        navController = Navigation.findNavController(this,R.id.auth_host_fragment);

        if(AuthenticationRepository.getInstance().getUserLoggedMutableLiveData().getValue() == false)
        {
            navController.navigate(R.id.signInFragment);
        }
        else {
            Credential.setCurrentUser(authViewModel.getUserData().getValue());
            Intent intent = new Intent(this, MainActivity.class);
            startActivity(intent);
        }
    }
    public AuthenticationViewModel getAuthViewModel()
    {
        return authViewModel;
    }
    public NavController getNavController() {return navController;}

}