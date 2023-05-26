package com.example.nativemovieapp.Fragments;

import android.os.Bundle;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.text.TextUtils;
import android.util.Log;

import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.nativemovieapp.Model.Comment;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.adapter.CommentAdapter;
import com.example.nativemovieapp.viewmodel.CommentViewModel;

import java.util.List;


public class MovieDetailFragmentComments extends Fragment {

    private Fragment mParentFragment;
    private int midCurrent;
    CommentViewModel cmtVM;
    boolean isInputEmpty;

    RecyclerView commentRCV;
    CommentAdapter adapter;
    LinearLayoutManager linearLayoutManager;

    public MovieDetailFragmentComments(Fragment parentFragment, int idCurrent) {
        mParentFragment = parentFragment;
        midCurrent = idCurrent;
    }

    EditText inputField;
    Button sendButton;
    TextView emptyView;

    @Override
    public void onCreate(@Nullable @org.jetbrains.annotations.Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        cmtVM = new ViewModelProvider(this).get(CommentViewModel.class);
        cmtVM.loadComment(midCurrent);

    }

    @Nullable
    @Override
    public View onCreateView(LayoutInflater inflater, @Nullable ViewGroup container, Bundle savedInstanceState) {
        View root = inflater.inflate(R.layout.fragment_comments, container, false);
        inputField = root.findViewById(R.id.input_field);
        sendButton = root.findViewById(R.id.sendBtn);
        commentRCV = root.findViewById(R.id.commentRCV);
        emptyView = root.findViewById(R.id.empty_state);


        //Set hàm gửi comment

        sendButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //Kiểm tra input
                String input = inputField.getText().toString();
                isInputEmpty = TextUtils.isEmpty(input.trim());
                Log.d("CMT", "isInputEmpty: " + isInputEmpty);
                Log.d("CMT", "Input: " + input);
                if (!isInputEmpty) {
                    cmtVM.sendComment(midCurrent, inputField);
                    inputField.setText("");
                    Log.d("CMT", "Sending");
                }
            }
        });

        ObserveChange();
        return root;
    }

    public void ObserveChange() {
        cmtVM.getListComment().observe(getViewLifecycleOwner(), new Observer<List<Comment>>() {
            @Override
            public void onChanged(List<Comment> comments) {
                if (comments != null) {
                    Log.d("CMT", comments.toString());
                    linearLayoutManager = new LinearLayoutManager(getContext(), RecyclerView.VERTICAL, false);
                    adapter = new CommentAdapter(comments);
                    updateEmptyStateVisibility(adapter, emptyView);
                    commentRCV.setLayoutManager(linearLayoutManager);
                    commentRCV.setAdapter(adapter);
                }
            }
        });
    }

    private void updateEmptyStateVisibility(RecyclerView.Adapter adapter, TextView emptyStateTextView) {
        if (adapter.getItemCount() == 0) {
            emptyStateTextView.setVisibility(View.VISIBLE);
        } else {
            emptyStateTextView.setVisibility(View.GONE);
        }
    }
}

