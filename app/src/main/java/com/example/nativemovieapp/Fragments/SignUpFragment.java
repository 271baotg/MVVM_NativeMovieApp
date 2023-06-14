package com.example.nativemovieapp.Fragments;

import static com.example.nativemovieapp.utils.Validator.isValidEmail;
import static com.example.nativemovieapp.utils.Validator.isValidPassword;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

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

import com.example.nativemovieapp.AuthenticationActivity;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.viewmodel.AuthenticationViewModel;
import com.google.android.gms.tasks.Task;
import com.google.firebase.auth.AuthResult;
import com.google.firebase.auth.FirebaseUser;
import com.thecode.aestheticdialogs.AestheticDialog;
import com.thecode.aestheticdialogs.DialogAnimation;
import com.thecode.aestheticdialogs.DialogStyle;
import com.thecode.aestheticdialogs.DialogType;


public class SignUpFragment extends Fragment {

    private NavController navController;
    private TextView tvSignIn;
    private Button btnSignUp;
    private EditText edtEmail;
    private EditText edtPassword;
    private EditText edtConfirmPassword;
    private EditText edtUsername;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_sign_up, container, false);
        initView(root);
        return root;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        initListener();
        navController = Navigation.findNavController(requireView());
    }
    private void initListener() {
        tvSignIn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                ((AuthenticationActivity) getActivity()).getNavController().navigate(R.id.signInFragment);
            }
        });
        btnSignUp.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String username = edtUsername.getText().toString().trim();
                String email = edtEmail.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                String confirmPass = edtConfirmPassword.getText().toString().trim();
                if(email.length() * pass.length() * confirmPass.length() == 0) {
                    AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.WARNING)
                            .setTitle("Warning")
                            .setMessage("Some field are missing")
                            .setGravity(Gravity.TOP)
                            .setAnimation(DialogAnimation.IN_OUT)
                            .show();
                    //Toast.makeText(getContext(), "Some field are missing! Please fill all", Toast.LENGTH_SHORT).show();
                } else if(!isValidEmail(email)){
                    //Toast.makeText(getContext(), "Email is not valid, please correct it", Toast.LENGTH_SHORT).show();
                    AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.WARNING)
                            .setTitle("Warning")
                            .setMessage("Email is not valid")
                            .setGravity(Gravity.TOP)
                            .setAnimation(DialogAnimation.IN_OUT)
                            .show();
                }
                else if(!isValidPassword(pass)){
                    AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.ERROR)
                            .setTitle("Warning")
                            .setMessage("Your password need to:\n" +
                            "\tHave minimum length 8 and maximum 20\n" +
                            "\tHave to contains at least one special character")
                            .setGravity(Gravity.TOP)
                            .setAnimation(DialogAnimation.IN_OUT)
                            .show();
//                    Toast.makeText(getContext(), "Your password need to:\n" +
//                            "\tHave minimum length 8 and maximum 20\n" +
//                            "\tHave to contains at least one special character", Toast.LENGTH_SHORT).show();
                }else if (!(pass.equals(confirmPass))) {
                    AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.WARNING)
                            .setTitle("Warning")
                            .setMessage("Password doesn't match")
                            .setGravity(Gravity.TOP)
                            .setAnimation(DialogAnimation.IN_OUT)
                            .show();
//                    Toast.makeText(getContext(), "Password doesn't match", Toast.LENGTH_SHORT).show();
                } else{
                    getViewModel().register(email, pass, new AuthenticationViewModel.AuthViewModelCallBack() {
                        @Override
                        public void onLoginCompleted(LiveData<FirebaseUser> user) {

                        }

                        @Override
                        public void onRegisterCompleted(Task<AuthResult> task) {
                            if(task.isSuccessful())
                            {
                                AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.SUCCESS)
                                        .setTitle("Success")
                                        .setMessage("Register is successful, please check your email")
                                        .setGravity(Gravity.TOP)
                                        .setAnimation(DialogAnimation.IN_OUT)
                                        .show();
//                                Toast.makeText(getActivity(), "Register is successful, please verify", Toast.LENGTH_SHORT).show();
                                getViewModel().updateDisplayName(username);
                                getViewModel().sendEmailVerification(new AuthenticationViewModel.SendEmailVerificationListener() {
                                    @Override
                                    public void onCompleted(Task<Void> task) {
                                        if(task.isSuccessful()){
                                            AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.SUCCESS)
                                                    .setTitle("Success")
                                                    .setMessage("Verification email was send, please check your email")
                                                    .setGravity(Gravity.TOP)
                                                    .setAnimation(DialogAnimation.IN_OUT)
                                                    .show();
//                                            Toast.makeText(getContext(), "User registered successfully", Toast.LENGTH_SHORT).show();
                                        }else {
                                            AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.ERROR)
                                                    .setTitle("Error")
                                                    .setMessage(task.getException().getMessage().trim())
                                                    .setGravity(Gravity.TOP)
                                                    .setAnimation(DialogAnimation.IN_OUT)
                                                    .show();

//                                            Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                        }
                                    }
                                });
                            }
                            else
                            {
                                AestheticDialog show = new AestheticDialog.Builder(getActivity(), DialogStyle.EMOTION, DialogType.ERROR)
                                        .setTitle("ERROR")
                                        .setMessage(task.getException().getMessage().toString())
                                        .setGravity(Gravity.TOP)
                                        .setAnimation(DialogAnimation.IN_OUT)
                                        .show();
//                                Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                            }
                        }
                    });
                }
            }
        });
    }
    //function
    void initView(View root)
    {
        tvSignIn = root.findViewById(R.id.tv_sign_in);
        btnSignUp = root.findViewById(R.id.btn_sign_up);
        edtEmail = root.findViewById(R.id.edt_email);
        edtPassword = root.findViewById(R.id.edt_pass);
        edtConfirmPassword = root.findViewById(R.id.edt_confirm_pass);
        edtUsername = root.findViewById(R.id.edt_username);
    }

    AuthenticationViewModel getViewModel()
    {
        return ((AuthenticationActivity) requireActivity()).getAuthViewModel();
    }

}