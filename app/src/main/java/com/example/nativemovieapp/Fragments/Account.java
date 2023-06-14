package com.example.nativemovieapp.Fragments;

import static android.app.Activity.RESULT_OK;

import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;

import android.provider.MediaStore;
import android.renderscript.ScriptGroup;
import android.text.InputType;
import android.text.TextUtils;
import android.text.method.HideReturnsTransformationMethod;
import android.text.method.PasswordTransformationMethod;
import android.util.Patterns;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;
import android.widget.Toast;

import com.example.nativemovieapp.AuthenticationActivity;
import com.example.nativemovieapp.R;
import com.example.nativemovieapp.utils.Validator;
import com.example.nativemovieapp.viewmodel.AccountViewModel;
import com.example.nativemovieapp.viewmodel.AuthenticationViewModel;
import com.google.android.gms.tasks.Task;
import com.google.android.material.tabs.TabLayout;
import com.google.firebase.auth.FirebaseUser;
import com.squareup.picasso.Picasso;

import java.io.IOException;


public class Account extends Fragment {
    private static final int PICK_IMAGE_REQUEST = 1;
    private AuthenticationViewModel authenticationViewModel;
    private AccountViewModel accountViewModel;
    private Button btnUpdateProfile;
    private Button btnLogout;
    private Button btnTest;
    private ImageButton btnEditImage;
    private ImageView imgAvatar;
    private TextView tvCancel;
    private EditText edtEmail;
    private EditText edtName;
    private LinearLayout llEditProfile;
    int edtNameChangeCount;

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

        ObserveChange();

        return root;
    }


    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
        initListener(requireView());
    }


    void initView(View root) {
        edtName = root.findViewById(R.id.edt_name);
        btnUpdateProfile = root.findViewById(R.id.btn_update_profile);
        btnEditImage = root.findViewById(R.id.btn_edit_img);
        btnLogout = root.findViewById(R.id.btn_sign_out);
        imgAvatar = root.findViewById(R.id.imgv_avatar);
        llEditProfile = root.findViewById(R.id.ll_edit);
        tvCancel = root.findViewById(R.id.tv_cancel);
        edtEmail = root.findViewById(R.id.edt_email);
        btnTest = root.findViewById(R.id.btn_test);
        //edtPhoneNumber = root.findViewById(R.id.edt_phone_number);
    }

    private void initListener(View root) {

        btnUpdateProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = edtName.getText().toString().trim();
                authenticationViewModel.updateDisplayName(name);
                btnUpdateProfile.setEnabled(false);
                edtNameChangeCount = 0;
                edtName.setEnabled(false);
                tvCancel.setVisibility(View.GONE);
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

        llEditProfile.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUpdateProfile.setEnabled(true);
                tvCancel.setVisibility(View.VISIBLE);
                edtName.setEnabled(true);
            }
        });

        tvCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                btnUpdateProfile.setEnabled(false);
                edtName.setEnabled(false);
                edtName.setText(authenticationViewModel.getUserData().getValue().getDisplayName());
                tvCancel.setVisibility(View.INVISIBLE);

            }
        });

        btnTest.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                openChangePasswordDialog(Gravity.CENTER);
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
        if (user.getDisplayName() != null) {
            edtName.setText(user.getDisplayName());
        }
        if (user.getPhotoUrl() == null) {
            imgAvatar.setImageResource(R.drawable.account_icon);
        } else {
            Picasso.get()
                    .load(user.getPhotoUrl())
                    .fit()
                    .into(imgAvatar);
        }
        if (user.getEmail() != null) {
            edtEmail.setText(user.getEmail());
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
    public void openChangePasswordDialog(int gravity) {
        final Dialog dialog = new Dialog(getContext());
        dialog.requestWindowFeature(Window.FEATURE_NO_TITLE);
        //Set layout cho dialog
        dialog.setContentView(R.layout.dialog_change_password);
        Window window = dialog.getWindow();
        if (window == null) {
            return;
        }
        window.setLayout(WindowManager.LayoutParams.MATCH_PARENT, WindowManager.LayoutParams.WRAP_CONTENT);
        window.setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));

        WindowManager.LayoutParams windowAttribute = window.getAttributes();

        windowAttribute.gravity = gravity;
        window.setAttributes(windowAttribute);

        //Khởi tạo view trong dialog
        EditText edtOldPassword = dialog.findViewById(R.id.edt_old_password);
        EditText edtNewPassword = dialog.findViewById(R.id.edt_new_password);
        EditText edtConfirmPassword = dialog.findViewById(R.id.edt_confirm_password);
        Button btnCancel = dialog.findViewById(R.id.btn_cancel);
        Button btnChange = dialog.findViewById(R.id.btn_change);
        ImageView hideShowOldPass = dialog.findViewById(R.id.iv_hide_show_old_pass);
        ImageView hideShowNewPass = dialog.findViewById(R.id.iv_hide_show_new_pass);
        ImageView hideShowConfirmPass = dialog.findViewById(R.id.iv_hide_show_confirm_pass);
        //Listener
        btnCancel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                dialog.dismiss();
            }
        });
        btnChange.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String oldPassword = edtOldPassword.getText().toString().trim();
                String newPassword = edtNewPassword.getText().toString().trim();
                String confirmPassword = edtConfirmPassword.getText().toString().trim();
                if(!Validator.isValidPassword(newPassword)){
                    Toast.makeText(getContext(), "Your password need to:\n\tHave minimum length 8 and maximum 20\n\tHave to contains at least one special character", Toast.LENGTH_SHORT).show();
                    return;
                }

                if (oldPassword.length() * newPassword.length() * confirmPassword.length() == 0) {
                    Toast.makeText(getContext(), "Please fill all the information required", Toast.LENGTH_SHORT).show();
                } else {
                    //Reauthenticate
                    authenticationViewModel.reAuthenticate(oldPassword, new AuthenticationViewModel.ReAuthenticateListener() {
                        @Override
                        public void onCompleted(Task<Void> task) {
                            if (!task.isSuccessful()) {
                                Toast.makeText(getContext(), "Old password is not correct!", Toast.LENGTH_SHORT).show();
                            } else {
                                if (!newPassword.equals(confirmPassword)) {
                                    Toast.makeText(getContext(), "Confirm password is not match! Please check again !", Toast.LENGTH_SHORT).show();
                                } else {
                                    authenticationViewModel.updatePassword(newPassword, new AuthenticationViewModel.UpdatePasswordListener() {
                                        @Override
                                        public void onCompleted(Task<Void> task) {
                                            if (task.isSuccessful()) {
                                                Toast.makeText(getContext(), "Changing password is successful", Toast.LENGTH_SHORT).show();
                                            } else {
                                                Toast.makeText(getContext(), task.getException().getMessage(), Toast.LENGTH_SHORT).show();
                                            }
                                        }
                                    });
                                }
                            }
                        }
                    });
                    //Update pass

                }


            }
        });

        //Hide show Edit Text
        hideShowOldPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtOldPassword.getInputType() == InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD)
                {
                    edtOldPassword.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    hideShowOldPass.setImageResource(R.drawable.icon_hide_password);
                } else {
                    edtOldPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hideShowOldPass.setImageResource(R.drawable.icon_show_password);
                }
            }
        });
        hideShowNewPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtNewPassword.getInputType() == InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD)
                {
                    edtNewPassword.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    hideShowNewPass.setImageResource(R.drawable.icon_hide_password);
                } else {
                    edtNewPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hideShowNewPass.setImageResource(R.drawable.icon_show_password);
                }
            }
        });
        hideShowConfirmPass.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if(edtConfirmPassword.getInputType() == InputType.TYPE_CLASS_TEXT + InputType.TYPE_TEXT_VARIATION_PASSWORD)
                {
                    edtConfirmPassword.setInputType(InputType.TYPE_TEXT_FLAG_MULTI_LINE);
                    hideShowConfirmPass.setImageResource(R.drawable.icon_hide_password);
                } else {
                    edtConfirmPassword.setInputType(InputType.TYPE_CLASS_TEXT | InputType.TYPE_TEXT_VARIATION_PASSWORD);
                    hideShowConfirmPass.setImageResource(R.drawable.icon_show_password);
                }
            }
        });

        dialog.show();
    }
    //Listener
    public interface ConfirmationDialogListener {
        void onConfirmed();

        void onCancelled();
    }

}