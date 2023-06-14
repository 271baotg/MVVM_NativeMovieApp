package com.example.nativemovieapp.Fragments;

import android.app.AlertDialog;
import android.content.Intent;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;

import android.text.TextUtils;
import android.util.Log;
import android.util.Patterns;
import android.view.Gravity;
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
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;


public class SignInFragment extends Fragment {

    private Button btnSignIn;
    private TextView tvSignUp;
    private EditText edtEmail;
    private EditText edtPass;
    private TextView tvForgotPassword;


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
//                String email = "annguyeen0@gmail.com";
//                String pass = "123123";

                if(email.isEmpty()||pass.isEmpty())
                {
                    AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.ERROR)
                            .setTitle("ERROR")
                            .setMessage("Email or Password is missing. Please fill it")
                            .setGravity(Gravity.TOP)
                            .setAnimation(DialogAnimation.IN_OUT)
                            .show();
//                    Toast.makeText(getActivity(), "Email or Password is missing. Please fill it", Toast.LENGTH_SHORT).show();
                }else{

                    ((AuthenticationActivity) getActivity()).getAuthViewModel().login(email, pass, new AuthenticationViewModel.AuthViewModelCallBack() {
                        @Override
                        public void onLoginCompleted(LiveData<FirebaseUser> user) {
                            if(user == null){
                                AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.ERROR)
                                        .setTitle("ERROR")
                                        .setMessage("Your email or password is not correct")
                                        .setGravity(Gravity.TOP)
                                        .setAnimation(DialogAnimation.IN_OUT)
                                        .show();
                                //Toast.makeText(getActivity(), "Your email or password is not correct", Toast.LENGTH_SHORT).show();
                            }
                            else if(!user.getValue().isEmailVerified()){
                                getViewModel().sendEmailVerification(new AuthenticationViewModel.SendEmailVerificationListener() {
                                    @Override
                                    public void onCompleted(Task<Void> task) {
                                        AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.WARNING)
                                                .setTitle("Warning")
                                                .setMessage("Please verify your email")
                                                .setGravity(Gravity.TOP)
                                                .setAnimation(DialogAnimation.IN_OUT)
                                                .show();
//                                        Toast.makeText(getActivity(), "Please verify your email", Toast.LENGTH_SHORT).show();
                                    }
                                });
                            }else{
                                Credential.setCurrentUser(((AuthenticationActivity) getActivity()).getAuthViewModel().getUserData().getValue());
                                Intent intent = new Intent(requireActivity(), MainActivity.class);
                                startActivity(intent);
                                getActivity().finish();
                            }
                        }

                        @Override
                        public void onRegisterCompleted(Task<AuthResult> task) {

                        }
                    });
                }

            }
        });
        tvForgotPassword.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
                View dialogView = getLayoutInflater().inflate(R.layout.dialog_forgot, null);

                EditText emailBox = dialogView.findViewById(R.id.edt_email);
                builder.setView(dialogView);
                AlertDialog dialog = builder.create();

                dialogView.findViewById(R.id.btn_reset).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        String email = emailBox.getText().toString().trim();
                        if(TextUtils.isEmpty(email) && !Patterns.EMAIL_ADDRESS.matcher(email).matches()){
                            Toast.makeText(getContext(), "Enter your email!", Toast.LENGTH_SHORT).show();
                            return;
                        }
                        getViewModel().sendEmailResetPassword(email, new AuthenticationViewModel.SendPasswordResetEmailListener() {
                            @Override
                            public void onCompleted(Task<Void> task) {
                                if(task.isSuccessful()){
                                    Toast.makeText(getContext(), "Please check your email", Toast.LENGTH_SHORT).show();

                                }
                                else {
                                    Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                }
                            }
                        });
                    }
                });
                dialogView.findViewById(R.id.btn_cancel).setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        dialog.dismiss();
                    }
                });
                if(dialog.getWindow() != null){
                    dialog.getWindow().setBackgroundDrawable(new ColorDrawable(0));
                }
                dialog.show();
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
        tvForgotPassword = requireView().findViewById(R.id.tv_forgot_password);
    }
    AuthenticationViewModel getViewModel()
    {
        return ((AuthenticationActivity) requireActivity()).getAuthViewModel();
    }
}