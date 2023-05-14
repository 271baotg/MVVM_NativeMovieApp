package com.example.nativemovieapp.Fragments;

import static android.app.Activity.RESULT_OK;

import static com.example.nativemovieapp.utils.ConfirmResult.NO;

import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;

import com.example.nativemovieapp.AuthenticationActivity;
import com.example.nativemovieapp.Firebase.StorageRepository;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.utils.ConfirmResult;
import com.example.nativemovieapp.viewmodel.AccountViewModel;
import com.example.nativemovieapp.viewmodel.AuthenticationViewModel;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.FileNotFoundException;
import java.io.IOException;


public class Account extends Fragment {

    private static final int PICK_IMAGE_REQUEST = 1;
    private AuthenticationViewModel authenticationViewModel;
    private AccountViewModel accountViewModel;
    private EditText edtName;
    private Button btnEditName;
    private Button btnEditImage;
    private Button btnLogout;
    private ImageView imgAvatar;
    private Uri ImageUri;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        authenticationViewModel = new ViewModelProvider(getActivity()).get(AuthenticationViewModel.class);
        accountViewModel = new ViewModelProvider(getActivity()).get(AccountViewModel.class);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        // Inflate the layout for this fragment
        View root = inflater.inflate(R.layout.fragment_account, container, false);
        initView(root);
        initListener(root);

        ObserveChange();

        return root;
    }


    void initView(View root) {
        edtName = root.findViewById(R.id.edt_name);
        btnEditName = root.findViewById(R.id.btn_edt_name);
        btnEditImage = root.findViewById(R.id.btn_edit_img);
        btnLogout = root.findViewById(R.id.btn_sign_out);
        imgAvatar = root.findViewById(R.id.imgv_avatar);

    }

    private void initListener(View root) {
        btnEditName.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                authenticationViewModel.updateDisplayName(name);
            }
        });
        btnEditImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openFileChooser();
            }
        });
        btnLogout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                getActivity().finish();
                authenticationViewModel.signOut();
                Intent intent = new Intent(getActivity(), AuthenticationActivity.class);
                startActivity(intent);
            }
        });
    }

    private void ObserveChange() {
        authenticationViewModel.getUserData().observe(getViewLifecycleOwner(), new Observer<FirebaseUser>() {
            @Override
            public void onChanged(FirebaseUser user) {
                showUserInformation(user);
            }
        });


    }

    private void openFileChooser() {
        Intent intent = new Intent();
        intent.setType("image/*");
        intent.setAction(Intent.ACTION_GET_CONTENT);
        startActivityForResult(intent, PICK_IMAGE_REQUEST);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, @Nullable Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == PICK_IMAGE_REQUEST && resultCode == RESULT_OK && data != null && data.getData() != null) {
            showConfirmationDialog(new ConfirmationDialogListener() {
                @Override
                public void onConfirmed() {
                    Uri imageUri = data.getData();
                    Picasso.get()
                            .load(imageUri)
                            .fit()
                            .into(imgAvatar);
                    try {

                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), imageUri);
                        accountViewModel.uploadAvatar(bitmap, new AccountViewModel.OnImageUploadListener() {
                            @Override
                            public void onImageUpload(Uri imageUrl) {
                                authenticationViewModel.updatePhotoUri(imageUrl);
                            }

                            @Override
                            public void onImageUploadError(String errorMessage) {

                            }
                        });
                    } catch (IOException e) {
                        throw new RuntimeException(e);
                    }

                }

                @Override
                public void onCancelled() {

                }
            });
        }
    }


    void showUserInformation(FirebaseUser user) {
        if(user.getDisplayName() != null){
            edtName.setText(user.getDisplayName());
        }
        if (user.getPhotoUrl() == null) {
            imgAvatar.setImageResource(R.drawable.account_icon);
        } else {
            Log.i("Test in account, show", user.getPhotoUrl().toString());
            Picasso.get()
                    .load(user.getPhotoUrl())
                    .fit()
                    .into(imgAvatar);
        }
    }

    private void showConfirmationDialog(final ConfirmationDialogListener listener) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getContext());
        builder.setTitle("Confirmation");
        builder.setMessage("Are you sure you want to change the image?");
        builder.setPositiveButton("Yes", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onConfirmed();
            }
        });

        builder.setNegativeButton("No", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                listener.onCancelled();
            }
        });
        AlertDialog alertDialog = builder.create();
        alertDialog.show();
    }

    public interface ConfirmationDialogListener {
        void onConfirmed();

        void onCancelled();
    }

}