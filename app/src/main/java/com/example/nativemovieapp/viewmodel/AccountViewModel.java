package com.example.nativemovieapp.viewmodel;

import android.graphics.Bitmap;
import android.net.Uri;
import android.util.Log;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.nativemovieapp.Firebase.StorageRepository;

public class AccountViewModel extends ViewModel {
    StorageRepository repository = StorageRepository.getInstance();
    private MutableLiveData<Uri> avatarUrl;

    public AccountViewModel()
    {
        avatarUrl = new MutableLiveData<>();
    }
    public LiveData<Uri> getAvatarUrl(){
        return avatarUrl;
    }

    public void uploadAvatar(Bitmap bm, OnImageUploadListener listener)
    {
        repository.uploadImage(bm, new StorageRepository.OnImageUploadListener() {
            @Override
            public void onImageUpload(Uri imageUrl) {
                avatarUrl.postValue(imageUrl);
                listener.onImageUpload(imageUrl);
                Log.i("Tét in AVM", imageUrl.toString());
            }

            @Override
            public void onImageUploadError(String errorMessage) {
                listener.onImageUploadError(errorMessage);
            }
        });
    }
    public interface OnImageUploadListener {
        void onImageUpload(Uri imageUrl);

        void onImageUploadError(String errorMessage);
    }

}
