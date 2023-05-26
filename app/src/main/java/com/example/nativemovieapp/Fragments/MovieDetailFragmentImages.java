package com.example.nativemovieapp;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

public class MovieDetailFragmentImages extends Fragment {
    EditText inputField;
    Button sendButton;

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_images, container, false);
        inputField = root.findViewById(R.id.input_field);
        sendButton = root.findViewById(R.id.sendBtn);
    }
}
