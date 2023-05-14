package com.example.nativemovieapp.Firebase;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.annotation.NonNull;
import androidx.lifecycle.MutableLiveData;

import com.example.nativemovieapp.Api.Credential;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.ByteArrayOutputStream;

public class StorageRepository {
    private static StorageRepository _ins;
    private StorageReference storageReference;
    private MutableLiveData<String> avatarUrl;
    public static StorageRepository getInstance() {
        if (_ins == null) {
            _ins = new StorageRepository();
        }
        return _ins;
    }
    private StorageRepository() {
        storageReference = FirebaseStorage.getInstance().getReferenceFromUrl("gs://mvvmmovienativeapp.appspot.com");
        avatarUrl = new MutableLiveData<>();
    }
    public void uploadImage(Bitmap bitmap, OnImageUploadListener listener)
    {
        String imageType = ".png";
        String filePrefix = "_avatar";
        String fileName = Credential.getCurrentUser().getUid().toString() + filePrefix + imageType;
        StorageReference avatarsRef = storageReference.child("Avatar").child(fileName);
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, baos);
        byte[] data = baos.toByteArray();

        UploadTask uploadTask = avatarsRef.putBytes(data);
        uploadTask.addOnFailureListener(new OnFailureListener() {
            @Override
            public void onFailure(@NonNull Exception exception) {
                listener.onImageUploadError(exception.getMessage());
            }
        }).addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
            @Override
            public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                avatarsRef.getDownloadUrl().addOnSuccessListener(new OnSuccessListener<Uri>() {
                    @Override
                    public void onSuccess(Uri uri) {
                        listener.onImageUpload(uri);
                    }
                }).addOnFailureListener(new OnFailureListener() {
                    @Override
                    public void onFailure(@NonNull Exception e) {
                        listener.onImageUploadError(e.getMessage());
                    }
                });
            }
        });
    }

    public interface OnImageUploadListener {
        void onImageUpload(Uri imageUrl);

        void onImageUploadError(String errorMessage);
    }
}
