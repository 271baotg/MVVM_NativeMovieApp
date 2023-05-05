package com.example.nativemovieapp.Fragments;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.LiveData;
import androidx.navigation.NavController;
import androidx.navigation.Navigation;

import android.util.Log;
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


public class SignUpFragment extends Fragment {

    private NavController navController;
    private TextView tvSignIn;
    private Button btnSignUp;
    private EditText edtEmail;
    private EditText edtPassword;

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
                Log.i("Edittext email:", edtEmail.toString());
                String email = edtEmail.getText().toString().trim();
                String pass = edtPassword.getText().toString().trim();
                ((AuthenticationActivity) requireActivity()).getAuthViewModel().register(email, pass, new AuthenticationViewModel.AuthViewModelCallBack() {
                    @Override
                    public void onLoginCompleted(LiveData<FirebaseUser> user) {

                    }

                    @Override
                    public void onRegisterCompleted(Task<AuthResult> task) {
                        if(task.isSuccessful())
                        {
                            Toast.makeText(getActivity(), "Sign up is successful!", Toast.LENGTH_SHORT).show();
                            Log.i("Test in suf", "IT HERRE");
                        }
                        else
                        {
                            Toast.makeText(getActivity(), task.getException().getMessage().toString(), Toast.LENGTH_SHORT).show();
                        }
                    }
                });
            }
        });
    }
    void initView(View root)
    {
        tvSignIn = root.findViewById(R.id.tv_sign_in);
        btnSignUp = root.findViewById(R.id.btn_sign_up);
        edtEmail = root.findViewById(R.id.edt_email);
        edtPassword = root.findViewById(R.id.edt_pass);
    }
}