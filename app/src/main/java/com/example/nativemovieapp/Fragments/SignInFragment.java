package com.example.nativemovieapp.Fragments;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativemovieapp.Api.Credential;
import com.example.nativemovieapp.AuthenticationActivity;
import com.example.nativemovieapp.MainActivity;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.viewmodel.AuthenticationViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.auth.UserProfileChangeRequest;


public class SignInFragment extends Fragment {

    private Button btnSignIn;
    private TextView tvSignUp;
    private EditText edtEmail;
    private EditText edtPass;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_sign_in, container, false);
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initView();
        initListener();


    }

    private void initListener() {
        btnSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String email = edtEmail.getText().toString().trim();
                String pass = edtPass.getText().toString().trim();
                if(email.isEmpty()||pass.isEmpty())
                {
                    Toast.makeText(getActivity(), "Email or Password is missing. Please fill it", Toast.LENGTH_SHORT).show();
                }else{

                    ((AuthenticationActivity) getActivity()).getAuthViewModel().login(email, pass, new AuthenticationViewModel.AuthViewModelCallBack() {
                        @Override
                        public void onLoginCompleted(LiveData<FirebaseUser> user) {
                            if(user == null){
                                Toast.makeText(getActivity(), "Your email or password is not correct", Toast.LENGTH_SHORT).show();
                            }else{

                                FirebaseUser temp = ((AuthenticationActivity) getActivity()).getAuthViewModel().getUserData().getValue();
                                Credential.setCurrentUser(((AuthenticationActivity) getActivity()).getAuthViewModel().getUserData().getValue());
                                ((AuthenticationActivity) getActivity()).getAuthViewModel().update();
                                Intent intent = new Intent(requireActivity(), MainActivity.class);
                                startActivity(intent);
                            }
                        }

                        @Override
                        public void onRegisterCompleted(Task<AuthResult> task) {

                        }
                    });
                }

            }
        });
        tvSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthenticationActivity) getActivity()).getNavController().navigate(R.id.signUpFragment);
            }
        });
    }

    private void initView() {
        btnSignIn = requireView().findViewById(R.id.btn_sign_in);
        tvSignUp = requireView().findViewById(R.id.tv_sign_up);
        edtEmail = requireView().findViewById(R.id.edt_email);
        edtPass = requireView().findViewById(R.id.edt_pass);
    }

}